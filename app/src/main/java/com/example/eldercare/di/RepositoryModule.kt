package com.example.eldercare.di

import com.example.eldercare.data.repository.auth.AuthRepositoryImpl
import com.example.eldercare.data.repository.basicInfo.BasicInfoRepositoryImpl
import com.example.eldercare.domain.repository.AuthRepository
import com.example.eldercare.domain.repository.BasicInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindBasicInfoRepository(impl: BasicInfoRepositoryImpl): BasicInfoRepository
}
