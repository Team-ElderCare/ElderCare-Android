package com.example.eldercare.data.datasource.remote.basicInfo

import com.example.eldercare.data.api.ProtectedApi
import com.example.eldercare.data.api.UserApi
import com.example.eldercare.data.model.request.ProtectedRegistrationRequest
import com.example.eldercare.data.model.request.UserRegistrationRequest
import com.example.eldercare.domain.model.basicInfo.Address
import com.example.eldercare.domain.model.basicInfo.Relationship
import okhttp3.MultipartBody
import javax.inject.Inject

class BasicInfoDataSource
    @Inject
    constructor(
        private val usersApi: UserApi,
        private val protectedApi: ProtectedApi,
    ) {
        suspend fun postUsersRegistration(
            username: String,
            phoneNumber: String,
            relationship: Relationship,
            userImageUrl: MultipartBody.Part,
        ): Result<String> =
            runCatching {
                usersApi.postUsersRegistration(
                    UserRegistrationRequest(
                        username,
                        phoneNumber,
                        relationship,
                        userImageUrl,
                    ),
                ).let { response ->
                    if (response.isSuccessful) {
                        response.body()!!
                    } else {
                        throw Exception("Failed to register user")
                    }
                }
            }

        suspend fun postProtectedRegistration(
            protectedName: String,
            protectedBirthDate: String,
            protectedPhoneNumber: String,
            address: Address,
        ): Result<String> =
            runCatching {
                protectedApi.postProtectedRegistration(
                    ProtectedRegistrationRequest(
                        protectedName,
                        protectedBirthDate,
                        protectedPhoneNumber,
                        address,
                    ),
                ).let { response ->
                    if (response.isSuccessful) {
                        response.body()!!
                    } else {
                        throw Exception("Failed to register protected")
                    }
                }
            }
    }
