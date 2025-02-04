package com.example.eldercare.data.api

import com.example.eldercare.data.model.request.KakaoLoginRequest
import com.example.eldercare.data.model.response.LoginResponse
import com.example.eldercare.data.model.response.UserInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("$VERSION/$AUTH/$LOGIN")
    suspend fun postLogin(
        @Body body: String,
    )

    companion object {
        const val VERSION = "v1"
        const val AUTH = "auth"
        const val USERS = "users"
        const val LOGIN = "login"
        const val LOGOUT = "logout"
    }

    @POST("auth/kakao/login")
    suspend fun kakaoLogin(@Body request: KakaoLoginRequest): Response<LoginResponse>

    @GET("auth/me")
    suspend fun getUserInfo(): Response<UserInfoResponse>
}
