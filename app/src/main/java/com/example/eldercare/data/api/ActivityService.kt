package com.example.eldercare.data.api

import com.example.eldercare.presentation.ui.activity.UserActivityItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivityService {
    @GET("/activites")
    suspend fun getActivities(
        @Query("date") date: String,
    ): Result<List<UserActivityItem>>
}
