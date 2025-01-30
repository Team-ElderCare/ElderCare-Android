package com.example.eldercare.data.repository.auth

import com.example.eldercare.domain.model.UserInfo
import com.example.eldercare.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        // 필요한 의존성 주입
    ) : AuthRepository {
        override suspend fun getUserInfo(): UserInfo {
            // 실제 API 호출이나 데이터 로직 구현
            return UserInfo(groups = listOf()) // 임시 구현
        }
    }
