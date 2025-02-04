package com.example.eldercare.data.model.response

import com.example.eldercare.data.model.UserDto

data class LoginResponse(
    val accessToken: String,
    val user: UserDto
)
