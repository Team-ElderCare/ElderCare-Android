package com.example.eldercare.base.response

data class BaseResponse<T>(
    val message : String,
    val code : Int,
    val data : T?
)
