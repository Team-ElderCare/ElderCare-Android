package com.example.eldercare.base.response

// api 응답 처리
data class BaseResponse<T>(
    val message: String,
    val code: Int,
    val data: T?,
)
