package com.example.eldercare.data.model.response

import com.example.eldercare.domain.model.User

data class UserDtoResponse(
    val id: Long,
    val email: String,
    val name: String,
    val groupId: String?,
) {
    fun toUser() = User(id, email, name)
}
