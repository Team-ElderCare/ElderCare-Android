package com.example.eldercare.presentation.model

sealed class UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>()

    data class Error(val message: String) : UiState<Nothing>()

    object Loading : UiState<Nothing>()

    object Empty : UiState<Nothing>()
}
