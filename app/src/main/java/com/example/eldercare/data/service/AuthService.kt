package com.example.eldercare.data.service

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("$VERSION/$AUTH/$LOGIN")
    suspend fun postLogin(
        @Body body: String
    )

    companion object {
        const val VERSION = "v1"
        const val AUTH = "auth"
        const val USERS = "users"
        const val LOGIN = "login"
        const val LOGOUT = "logout"
    }
}
