package com.example.repo.network.di

import com.example.repo.network.NetworkService
import com.example.repo.network.NetworkServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkServiceModule {

    @Singleton
    @Binds
    abstract fun bindNetworkService(impl: NetworkServiceImpl): NetworkService

}

