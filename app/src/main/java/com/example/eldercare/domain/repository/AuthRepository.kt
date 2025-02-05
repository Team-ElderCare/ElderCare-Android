package com.example.eldercare.domain.repository

import com.example.eldercare.domain.model.UserInfo

interface AuthRepository {
    suspend fun kakaoLogin(code: String): Result<UserInfo>

    suspend fun getUserInfo(): Result<UserInfo>

    suspend fun isLocalToken(): Boolean

    suspend fun isLocalGroupId(): Boolean
}
