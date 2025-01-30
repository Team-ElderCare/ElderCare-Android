package com.example.eldercare.data.datasource.remote.auth

import com.example.eldercare.data.api.AuthApi
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authApi: AuthApi
) {
    // TODO -> 임시 변수
    suspend fun postLogin(body: String) {
        return authApi.postLogin(body)
    }
}
