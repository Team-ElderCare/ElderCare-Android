package com.example.eldercare.domain.model

data class UserInfo(
    val groups: List<String> = emptyList(),
    val id: Long,
    val email: String,
    val name: String,
    val groupId: String?
)
