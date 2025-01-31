package com.example.eldercare.di

import com.example.eldercare.data.api.AuthApi
import com.example.eldercare.di.qualifier.ElderCare
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideAuthApi(
        @ElderCare retrofit: Retrofit,
    ): AuthApi = retrofit.create(AuthApi::class.java)
}
