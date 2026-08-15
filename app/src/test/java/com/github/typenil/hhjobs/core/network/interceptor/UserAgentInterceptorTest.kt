package com.github.typenil.hhjobs.core.network.interceptor

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserAgentInterceptorTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `interceptor appends required user agent and accept headers`() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody("{}"))

        val interceptor = UserAgentInterceptor()
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val request = Request.Builder()
            .url(mockWebServer.url("/vacancies"))
            .build()

        client.newCall(request).execute().close()

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("hh-jobs-client/0.1 (typenil0@yahoo.com)", recordedRequest.getHeader("User-Agent"))
        assertEquals("hh-jobs-client/0.1 (typenil0@yahoo.com)", recordedRequest.getHeader("HH-User-Agent"))
        assertEquals("application/json", recordedRequest.getHeader("Accept"))
    }
}
