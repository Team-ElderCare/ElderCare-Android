package com.example.eldercare.domain.model.recent

// 감지  타입 : 감지안됨, 약장감지, 감지
data class UserRecentActivityData(
    val id: Int,
    val time: String,
    val title: String,
    val sensorType: String,
)
