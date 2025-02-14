package com.example.eldercare.data.datasource.remote.activity

import com.example.eldercare.data.api.ActivityService
import com.example.eldercare.presentation.ui.activity.UserActivityItem

class ActivityDataSource(
    private val activityService: ActivityService,
) {
    suspend fun getActivites(date: String): Result<List<UserActivityItem>> = activityService.getActivities(date)
}
