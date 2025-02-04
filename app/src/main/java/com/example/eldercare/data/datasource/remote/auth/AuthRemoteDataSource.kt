package com.example.eldercare.data.datasource.remote.auth

import com.example.eldercare.data.api.AuthApi
import com.example.eldercare.data.model.request.KakaoLoginRequest
import com.example.eldercare.data.model.response.LoginResponse
import javax.inject.Inject

// class AuthRemoteDataSource
//    @Inject
//    constructor(
//        private val authApi: AuthApi,
//    ) {
//        // TODO -> 임시 변수
//        suspend fun postLogin(body: String) {
//            return authApi.postLogin(body)
//        }
//    }
class AuthRemoteDataSource
    @Inject
    constructor(
        private val api: AuthApi,
    ) {
        suspend fun kakaoLogin(code: String): Result<LoginResponse> {
            return try {
                val response = api.kakaoLogin(KakaoLoginRequest(code))
                if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Login failed"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        suspend fun getUserInfo(): Result<LoginResponse> {
            return try {
                val response = api.getUserInfo()
                if (response.isSuccessful) {
                    // 여기서는 토큰이 필요없음
                    Result.success(
                        LoginResponse(
                            accessToken = "",
                            user = response.body()!!.user,
                        ),
                    )
                } else {
                    Result.failure(Exception("Failed to get user info"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
