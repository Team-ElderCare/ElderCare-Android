package com.example.eldercare.data.model.response

data class LoginResponse(
    val accessToken: String,
    val user: UserDtoResponse,
)
