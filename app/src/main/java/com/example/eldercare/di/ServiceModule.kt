package com.example.eldercare.di

import com.example.eldercare.data.service.AuthService
import com.example.eldercare.di.qualifier.ElderCare
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideAuthApi(
        @ElderCare retrofit: Retrofit,
    ): AuthService = retrofit.create(AuthService::class.java)
}
