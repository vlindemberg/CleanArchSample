package com.example.cleanarchsample

import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
abstract class BaseRepositoryUnitTest {

    protected lateinit var mockWebServer: MockWebServer
    protected lateinit var retrofit: Retrofit

    @Before
    open fun setUpBase() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @After
    open fun tearDownBase() {
        mockWebServer.shutdown()
    }

    protected fun enqueueResponse(
        code: Int = 200,
        body: String = "{}",
        headers: Map<String, String> = emptyMap()
    ) {
        val mockResponse = MockResponse()
            .setResponseCode(code)
            .setBody(body)

        headers.forEach { (key, value) ->
            mockResponse.addHeader(key, value)
        }

        mockWebServer.enqueue(mockResponse)
    }
}