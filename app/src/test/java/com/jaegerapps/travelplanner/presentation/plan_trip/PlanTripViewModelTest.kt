package com.jaegerapps.travelplanner.presentation.plan_trip

import com.google.common.truth.Truth.assertThat
import android.content.Context
import android.os.Build.VERSION_CODES.Q
import android.os.Looper.getMainLooper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jaegerapps.travelplanner.data.GptRepositoryImpl
import com.jaegerapps.travelplanner.data.remote.*
import com.jaegerapps.travelplanner.data.repository.GooglePlaceRepositoryImpl
import com.jaegerapps.travelplanner.domain.models.Itinerary.RequestItinerary
import com.jaegerapps.travelplanner.domain.models.google.PlaceLocationInfo
import com.jaegerapps.travelplanner.domain.repositories.GooglePlaceRepository
import com.jaegerapps.travelplanner.domain.repositories.GptRepository
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Q])
class PlanTripViewModelTest {
    private lateinit var gptRepository: GptRepository
    private lateinit var placeRepository: GooglePlaceRepository
    private lateinit var mockGptWebServer: MockWebServer
    private lateinit var mockPlaceWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var gptApi: GptApi
    private lateinit var googlePlaceApi: GooglePlaceApi
    private lateinit var planTripViewModel: PlanTripViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var fakeRequestSingle: RequestItinerary
    private lateinit var fakeRequestMulti: RequestItinerary
    private val context: Context = ApplicationProvider.getApplicationContext()

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {

        mockGptWebServer = MockWebServer()
        mockPlaceWebServer = MockWebServer()
        okHttpClient = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()
        gptApi = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockGptWebServer.url("/"))
            .build()
            .create(GptApi::class.java)
        googlePlaceApi = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockPlaceWebServer.url("/"))
            .build()
            .create(GooglePlaceApi::class.java)

        gptRepository = GptRepositoryImpl(
            api = gptApi
        )

        placeRepository = GooglePlaceRepositoryImpl(
            api = googlePlaceApi
        )

        planTripViewModel = PlanTripViewModel(
            gptRepository,
            placeRepository
        )
        sharedViewModel = SharedViewModel()

        Dispatchers.setMain(TestCoroutineDispatcher())

        fakeRequestSingle = RequestItinerary(
            days = "1",
            aboutTrip = "I am traveling to Seoul for the first time ever.",
            location = PlaceLocationInfo(
                37.5519,
                126.9918,
                "Seoul, South Korea"
            ),
        )
        fakeRequestMulti = RequestItinerary(
            days = "3",
            aboutTrip = "I am traveling to Seoul for the first time ever.",
            location = PlaceLocationInfo(
                37.5519,
                126.9918,
                "Seoul, South Korea"
            ),
        )
    }


    @After
    fun tearDown() {
        mockGptWebServer.shutdown()
        mockPlaceWebServer.shutdown()
        Dispatchers.resetMain()
    }


    @Test
    fun onSendQuery() = runBlocking {

        mockGptWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(valid_gpt_response_seoul)
        )

        sharedViewModel.requestState.requestItinerary = fakeRequestSingle



        planTripViewModel.onSendQuery(context, sharedViewModel)
        shadowOf(getMainLooper()).idle()
        Thread.sleep(1000)

        println(sharedViewModel._plannedItinerary.value)

        assertThat(sharedViewModel._plannedItinerary.value.dayPlan.planList).isNotEmpty()

    }

    @Test
    fun onMultiSend() = runBlocking {

        mockGptWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(valid_gpt_response_seoul)
        )

        sharedViewModel.requestState.requestItinerary = fakeRequestMulti



        planTripViewModel.onMultiDaySendQuery(context, sharedViewModel)
        shadowOf(getMainLooper()).idle()
        Thread.sleep(1000)

        assertThat(sharedViewModel._plannedItinerary.value.multiDayPlan).isNotEmpty()
        assertThat(sharedViewModel.requestState.requestItinerary.exclusionList).isNotEmpty()

        assertThat(sharedViewModel._plannedItinerary.value.durationOfStay).isNotEqualTo("1")

    }

    @Test
    fun onSearchAddressChange() {
        mockPlaceWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(valid_autocomplete_response)
        )
        shadowOf(getMainLooper()).idle()
        planTripViewModel.onSearchAddressChange("Paris")
        shadowOf(getMainLooper()).idle()
        Thread.sleep(1000)
        assertThat(planTripViewModel.state.predictions.predictions).isNotEmpty()
        assertThat(planTripViewModel.state.predictions.predictions.first().name).isEqualTo("Paris, France")

    }



    @Test
    fun onLocationNext() {
    }
}