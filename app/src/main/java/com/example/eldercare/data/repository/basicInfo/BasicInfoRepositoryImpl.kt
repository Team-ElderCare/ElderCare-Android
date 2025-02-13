package com.example.eldercare.data.repository.basicInfo

import com.example.eldercare.data.datasource.remote.basicInfo.BasicInfoDataSource
import com.example.eldercare.domain.model.basicInfo.Address
import com.example.eldercare.domain.model.basicInfo.Relationship
import com.example.eldercare.domain.repository.BasicInfoRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class BasicInfoRepositoryImpl
    @Inject
    constructor(
        private val basicInfoDataSource: BasicInfoDataSource
) : BasicInfoRepository {
    override suspend fun postProtectedRegistration(
        protectedName: String,
        protectedBirthDate: String,
        protectedPhoneNumber: String,
        address: Address
    ): Result<String> {
        return basicInfoDataSource.postProtectedRegistration(
            protectedName,
            protectedBirthDate,
            protectedPhoneNumber,
            address
        )
    }

    override suspend fun postUserRegistration(
        username: String,
        phoneNumber: String,
        relationship: Relationship,
        userImageUrl: MultipartBody.Part
    ): Result<String> {
        return basicInfoDataSource.postUsersRegistration(
            username, phoneNumber, relationship, userImageUrl
        )
    }
}
