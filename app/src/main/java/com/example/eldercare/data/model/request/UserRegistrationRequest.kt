package com.example.eldercare.data.model.request

import com.example.eldercare.domain.model.basicInfo.Relationship
import okhttp3.MultipartBody

data class UserRegistrationRequest(
    val username: String,
    val phoneNumber: String,
    val relationship: Relationship,
    val userImageUrl: MultipartBody.Part
)

