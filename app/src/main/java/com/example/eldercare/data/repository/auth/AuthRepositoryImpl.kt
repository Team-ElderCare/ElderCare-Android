package com.example.eldercare.data.repository.auth

import com.example.eldercare.data.datasource.remote.auth.AuthRemoteDataSource
import com.example.eldercare.domain.model.UserInfo
import com.example.eldercare.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val authRemoteDataSource: AuthRemoteDataSource,
    ) : AuthRepository {
        // TODO -> 임시 함수
        override suspend fun getUserInfo(): UserInfo {
            return UserInfo(groups = listOf())
        }
    }
