package com.example.eldercare.domain.repository.activity

import com.example.eldercare.presentation.ui.activity.UserActivityItem

interface ActivityRepository {
    suspend fun getActivities(date: String): Result<List<UserActivityItem>>
}
