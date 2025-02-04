package com.example.eldercare.data.repository.auth

import com.example.eldercare.data.datasource.remote.auth.AuthRemoteDataSource
import com.example.eldercare.data.local.auth.AuthLocalDataSource
import com.example.eldercare.domain.model.User
import com.example.eldercare.domain.model.UserInfo
import com.example.eldercare.domain.repository.AuthRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//class AuthRepositoryImpl
//    @Inject
//    constructor(
//        private val authRemoteDataSource: AuthRemoteDataSource,
//    ) : AuthRepository {
//        // TODO -> 임시 함수
//        override suspend fun getUserInfo(): UserInfo {
//            return UserInfo(groups = listOf())
//        }
//    }



class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource
) : AuthRepository {

    override suspend fun kakaoLogin(code: String): Result<UserInfo> = withContext(Dispatchers.IO) {
        try {
            val response = remoteDataSource.kakaoLogin(code)
            response.onSuccess { loginResponse ->
                localDataSource.saveToken(loginResponse.accessToken)
                localDataSource.saveGroupId(loginResponse.user.groupId ?: "")
            }

            response.map { loginResponse ->
                UserInfo(
                    id = loginResponse.user.id,
                    email = loginResponse.user.email,
                    name = loginResponse.user.name,
                    groupId = loginResponse.user.groupId
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserInfo(): Result<UserInfo> = withContext(Dispatchers.IO) {
        try {
            remoteDataSource.getUserInfo().map { userResponse ->
                UserInfo(
                    id = userResponse.user.id,
                    email = userResponse.user.email,
                    name = userResponse.user.name,
                    groupId = userResponse.user.groupId
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun isLocalToken(): Boolean = withContext(Dispatchers.IO) {
        localDataSource.getToken() != null
    }

    override suspend fun isLocalGroupId(): Boolean = withContext(Dispatchers.IO) {
        localDataSource.getGroupId() != null
    }
}
