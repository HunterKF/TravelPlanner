package com.jaegerapps.travelplanner.data.repository

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.jaegerapps.travelplanner.data.models.google_places.PhotoReferenceDto
import com.jaegerapps.travelplanner.data.models.google_places.PlaceDto
import com.jaegerapps.travelplanner.data.models.google_places.PlaceResultDto
import com.jaegerapps.travelplanner.data.remote.GooglePlaceApi
import com.jaegerapps.travelplanner.data.remote.valid_nearby_search_parks
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class GooglePlaceRepositoryImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var repository: GooglePlaceRepositoryImpl
    private lateinit var api: GooglePlaceApi
    private lateinit var gson: Gson
    private lateinit var expectedPlaceResultDto: PlaceResultDto
    private lateinit var locationString: String

    @Before
    fun setUp() {
        gson = Gson()
        mockWebServer = MockWebServer()
        okHttpClient = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()
        api = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create()

        repository = GooglePlaceRepositoryImpl(
            api = api
        )
        expectedPlaceResultDto = PlaceResultDto(
            "AUjq9jknGpWi5DMYO9EL9vPW_t4VsqgC_d5O__xDMmyLO7zASM9VvWedsTKt48OdrCgWjdHNgq53TY-OGlEiHG3YrSKHRLx7lH8dqKuA5qsRr03hFD3LRzlD7HNkg_-jMyNQ9FAbKVLMfSOuhItdxb-ML33hLQM8KHpR5tXNRaWHaBjC2WMfRle5Fpi58Ip2lD_L3A8exm4YMMN3MKup8uBHDLDRurNkQOMvA8R5W_1HnFc3QZ36toNr99FlARU-5st2FK4MzQ3fdyEa_DweYOf_TV36jqTQ5Ydkntujygip12bD7xOKu-ykRfCv62fyeH_jc1dmzAXPzQET42Oj489rU-YRPy-uXN91au8aCcwlJQVGagXfmOxslc1EnA0mVyAEkKLp3-Qg6gZbzEhfOKdZSWzv2T_ybjjSsKMyJRWXrw6g4ijBMfe27-fh91Z4o2f1m1_qdBphlJP7qJNf",
            places = listOf(
                PlaceDto(
                    name = "NaeSooDong Park (내수동 근린 공원)",
                    address = "123 Hunter St",
                    photos = null,
                    types = listOf("park", "point_of_interest", "establishment")
                ),
                PlaceDto(
                    name = "Itaewon Children’s Park",
                    address = "123 Krez St",
                    photos = arrayOf(
                        PhotoReferenceDto(
                            "AUjq9jmzGpVWH_FmiuIGcIbmE57HOyo3XhafjPIUGFjxi30dSESJc5dX5kq_ylLCJUM-c_opFVYR7sbwL99ZNTfaml5S480kUM_K24yI7BSLkD3ioNUDQ8GF3Iky3n-PVQBKzHrmsoP3zG5AHyyRD1SbfG3efcmHDvlxLPXu4Qlc6uiHCkMA",
                        )
                    ),
                            types = listOf ("park", "point_of_interest", "establishment"
                )
            ),
            PlaceDto(
                name = "Son Gijeong Park",
                address = "123 Alex St",
                photos = arrayOf(
                    PhotoReferenceDto(

                "AUjq9jkgRZW-V2DY2m9XNFha03j2OXtG0YmqMBg1euCqB_6_ocQEp1fVFw8RoLjNOBOM60ZKQsMW8DbCXmPVbuoLOJ3zyau1iBRHGVpLsPuJPqClcktJ4EwFQuNPVijxB7cfkxC4sY-8CproDi7RQ5tuDoR30kIy0Asbf0lJ41ooNuGuxq6r",
                    )
                ),

                types = listOf(
                    "park",
                    "tourist_attraction",
                    "point_of_interest",
                    "establishment"
                )
            ),
        )
        )
        locationString = ""
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getPlaces() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(valid_nearby_search_parks)
        )
        val places = repository.getPlaces(locationString, "park")


        println(places)
        assertThat(places.isSuccess).isTrue()
    }

    @Test
    fun autoComplete() {
    }
}