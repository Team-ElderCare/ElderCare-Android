package com.example.eldercare.data.api

import com.example.eldercare.data.model.request.ProtectedRegistrationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ProtectedApi {
    @POST("/protected/registration")
    suspend fun postProtectedRegistration(
        @Body body: ProtectedRegistrationRequest,
    ): Response<String>
}
