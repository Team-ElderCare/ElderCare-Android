package com.example.eldercare.domain.usecase

import com.example.eldercare.domain.model.UserInfo
import com.example.eldercare.domain.repository.AuthRepository
import javax.inject.Inject

// class PostSignInUseCase(
//    private val authRepository: AuthRepository,
// ) {
//    suspend operator fun invoke() {}
// }

// class PostSignInUseCase
//    @Inject
//    constructor(
//        private val authRepository: AuthRepository,
//    ) {
//        suspend operator fun invoke(): Result<UserInfo> =
//            runCatching {
//                authRepository.getUserInfo()
//            }
//    }

class PostSignInUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) {
        suspend operator fun invoke(): Result<UserInfo> = authRepository.getUserInfo() // 이미 Result를 반환하므로 runCatching 제거
    }
