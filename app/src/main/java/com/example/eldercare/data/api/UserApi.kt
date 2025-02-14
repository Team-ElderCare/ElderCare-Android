package com.example.eldercare.data.api

import com.example.eldercare.data.model.request.UserRegistrationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/users/registration")
    suspend fun postUsersRegistration(
        @Body body: UserRegistrationRequest,
    ): Response<String>
}
