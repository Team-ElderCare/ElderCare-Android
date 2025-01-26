package com.example.eldercare.domain.repository

import com.example.eldercare.domain.model.UserInfo

interface AuthRepository {
    suspend fun getUserInfo(): UserInfo
}
