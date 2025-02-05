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
class AuthRemoteDataSource @Inject constructor(
    private val api: AuthApi,
) {
    // runCatching을 사용하여 kakao 로그인 요청을 처리
    // 성공시 LoginResponse 반환, 실패시 Exception을 Result로 래핑하여 반환
    suspend fun kakaoLogin(code: String): Result<LoginResponse> = runCatching {
        api.kakaoLogin(KakaoLoginRequest(code)).let { response ->
            if (response.isSuccessful) {
                response.body()!!
            } else {
                throw Exception("Login failed")
            }
        }
    }

    // runCatching을 사용하여 유저 정보 요청을 처리
    // 성공시 액세스 토큰이 비어있는 LoginResponse 반환, 실패시 Exception을 Result로 래핑하여 반환
    suspend fun getUserInfo(): Result<LoginResponse> = runCatching {
        api.getUserInfo().let { response ->
            if (response.isSuccessful) {
                LoginResponse(
                    accessToken = "",
                    user = response.body()!!.user,
                )
            } else {
                throw Exception("Failed to get user info")
            }
        }
    }
}
