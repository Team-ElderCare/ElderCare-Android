package com.example.eldercare.domain.model.alarm

data class DrugAlarmData(
    val alarmId: Int,
    val alarmTime: List<AlarmInfo>,
    val drugName: String,
)

data class AlarmInfo(
    val alarmTime: String,
    val isActive: Boolean,
)
