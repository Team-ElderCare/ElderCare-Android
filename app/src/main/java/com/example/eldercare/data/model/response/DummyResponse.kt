package com.example.eldercare.data.model.response

data class DummyResponse(
    val test: String,
) {
    // TODO -> Domain model로 변경하는 부분 추가
    fun toDummy() = {}
}
