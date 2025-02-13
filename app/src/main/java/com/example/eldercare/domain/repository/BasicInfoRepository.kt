package com.example.eldercare.domain.repository

import com.example.eldercare.domain.model.basicInfo.Address
import com.example.eldercare.domain.model.basicInfo.Relationship
import okhttp3.MultipartBody

interface BasicInfoRepository {
    suspend fun postProtectedRegistration(protectedName: String,
                                          protectedBirthDate: String,
                                          protectedPhoneNumber: String,
                                          address: Address
    ): Result<String>

    suspend fun postUserRegistration(username: String,
                                     phoneNumber: String,
                                     relationship: Relationship,
                                     userImageUrl: MultipartBody.Part): Result<String>
}
