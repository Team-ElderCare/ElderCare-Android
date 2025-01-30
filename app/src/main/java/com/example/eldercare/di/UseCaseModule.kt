package com.example.eldercare.di

import com.example.eldercare.domain.repository.AuthRepository
import com.example.eldercare.domain.usecase.PostSignInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    // TODO -> 필요 할 때만 UseCase 사용
    @Provides
    @Singleton
    fun providesPostSignInUseCase(authRepository: AuthRepository): PostSignInUseCase = PostSignInUseCase(authRepository = authRepository)
}
