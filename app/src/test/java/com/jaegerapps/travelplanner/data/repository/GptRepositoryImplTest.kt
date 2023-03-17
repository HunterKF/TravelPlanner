package com.jaegerapps.travelplanner.data.repository


import com.google.common.truth.Truth.assertThat
import com.jaegerapps.travelplanner.data.GptRepositoryImpl
import com.jaegerapps.travelplanner.data.remote.GptApi
import com.jaegerapps.travelplanner.data.remote.invalidResponse
import com.jaegerapps.travelplanner.data.remote.validResponse
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

class GptRepositoryImplTest {

    private lateinit var repository: GptRepositoryImpl
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var api: GptApi

    @Before
    fun setUp() {
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

        repository = GptRepositoryImpl(
            api = api
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Send System Spec, valid response, returns result`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(validResponse)
        )
        val result = repository.sendSystemSpec()

        assertThat(result.isSuccess).isTrue()
    }
    @Test
    fun `Send System Spec, invalid response, returns failure`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(403)
                .setBody(validResponse)
        )
        val result = repository.sendSystemSpec()

        assertThat(result.isFailure).isTrue()
    }
    @Test
    fun `Send System Spec, malformed response, returns failure`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(invalidResponse)
        )
        val result = repository.sendSystemSpec()

        assertThat(result.isFailure).isTrue()
    }
    @Test
    fun sendSystemSpec() {
    }
}