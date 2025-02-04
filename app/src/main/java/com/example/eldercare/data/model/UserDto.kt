package com.example.eldercare.data.model

import com.example.eldercare.domain.model.User

data class UserDto(
    val id: Long,
    val email: String,
    val name: String,
    val groupId: String?,
) {
    fun toUser() = User(id, email, name)
}
