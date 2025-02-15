package com.example.eldercare.domain.usecase.user

import com.example.eldercare.domain.model.basicInfo.Relationship
import com.example.eldercare.domain.repository.BasicInfoRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class PostUserRegistrationUseCase
    @Inject
    constructor(
        private val basicInfoRepository: BasicInfoRepository,
    ) {
        suspend operator fun invoke(
            username: String,
            phoneNumber: String,
            relationship: Relationship,
            userImageUrl: MultipartBody.Part,
        ): Result<String> =
            basicInfoRepository.postUserRegistration(
                username,
                phoneNumber,
                relationship,
                userImageUrl,
            )
    }
