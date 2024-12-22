package com.example.eldercare.domain.usecase

import com.example.eldercare.domain.repository.AuthRepository

class PostSignInUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() {}
}
