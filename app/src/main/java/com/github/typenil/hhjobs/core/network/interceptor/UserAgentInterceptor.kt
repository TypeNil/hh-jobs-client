package com.github.typenil.hhjobs.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interceptor that appends required HeadHunter API identification headers to every HTTP request.
 * HeadHunter requires a unique User-Agent in the format `AppName/version (contact_email_or_url)`.
 */
@Singleton
class UserAgentInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val requestWithHeaders = originalRequest.newBuilder()
            .header(HEADER_USER_AGENT, USER_AGENT_VALUE)
            .header(HEADER_HH_USER_AGENT, USER_AGENT_VALUE)
            .header(HEADER_ACCEPT, ACCEPT_JSON_VALUE)
            .build()

        return chain.proceed(requestWithHeaders)
    }

    companion object {
        private const val HEADER_USER_AGENT = "User-Agent"
        private const val HEADER_HH_USER_AGENT = "HH-User-Agent"
        private const val HEADER_ACCEPT = "Accept"

        private const val USER_AGENT_VALUE = "hh-jobs-client/0.1 (typenil0@yahoo.com)"
        private const val ACCEPT_JSON_VALUE = "application/json"
    }
}
