package com.example.eldercare.data.interceptor

import com.example.eldercare.util.token.TokenManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { tokenManager.getAccessToken().firstOrNull() }
        val request = chain.request().newBuilder().apply {
            token?.let { addHeader(HEADER_AUTHORIZATION, it) }
        }.build()
        return chain.proceed(request)
    }

    companion object {
        const val HEADER_AUTHORIZATION = "token"
    }
}
