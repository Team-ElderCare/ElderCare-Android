package com.example.eldercare.domain.usecase.proTected

import com.example.eldercare.domain.model.basicInfo.Address
import com.example.eldercare.domain.repository.BasicInfoRepository
import javax.inject.Inject

class PostProtectedRegistrationUseCase
@Inject
constructor(
    private val basicInfoRepository: BasicInfoRepository,
) {
    suspend operator fun invoke(
        protectedName: String,
        protectedBirthDate: String,
        protectedPhoneNumber: String,
        address: Address
    ): Result<String> =
        basicInfoRepository.postProtectedRegistration(
            protectedName,
            protectedBirthDate,
            protectedPhoneNumber,
            address
        )
}
