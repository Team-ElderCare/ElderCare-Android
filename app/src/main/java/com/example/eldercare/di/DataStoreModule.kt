package com.example.eldercare.di

import android.content.Context
import com.example.eldercare.util.token.AccessTokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideTokenManager(
        @ApplicationContext context: Context,
    ): AccessTokenManager {
        return AccessTokenManager(context)
    }
}
