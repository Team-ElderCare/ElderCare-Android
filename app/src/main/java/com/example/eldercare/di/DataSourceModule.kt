package com.example.eldercare.di

import com.example.eldercare.data.datasource.remote.auth.AuthRemoteDataSource
import com.example.eldercare.data.datasource.remote.auth.AuthRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindAuthDataSource(authDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource
}
