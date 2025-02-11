package com.example.eldercare.data.repository.activity

import com.example.eldercare.data.datasource.remote.activity.ActivityDataSource
import com.example.eldercare.domain.repository.activity.ActivityRepository
import com.example.eldercare.presentation.ui.activity.UserActivityItem

class ActivityRepositoryImpl(
    private val activityDataSource: ActivityDataSource,
) : ActivityRepository {
    override suspend fun getActivities(date: String): Result<List<UserActivityItem>> = activityDataSource.getActivites(date)
}
