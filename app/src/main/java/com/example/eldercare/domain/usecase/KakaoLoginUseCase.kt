package com.example.eldercare.domain.usecase

import com.example.eldercare.domain.model.User
import com.example.eldercare.domain.repository.AuthRepository
import javax.inject.Inject

class KakaoLoginUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) {
        suspend operator fun invoke(code: String): Result<User> {
            return authRepository.kakaoLogin(code).map { userInfo ->
                User(
                    id = userInfo.id,
                    email = userInfo.email,
                    name = userInfo.name,
                )
            }
        }
    }
