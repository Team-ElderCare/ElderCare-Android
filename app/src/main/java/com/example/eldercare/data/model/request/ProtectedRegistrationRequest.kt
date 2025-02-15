package com.example.eldercare.data.model.request

import com.example.eldercare.domain.model.basicInfo.Address

data class ProtectedRegistrationRequest(
    val protectedName: String,
    val protectedBirthDate: String,
    val protectedPhoneNumber: String,
    val address: Address,
)
