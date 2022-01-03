package com.example.assetmanagement.domain.di

import com.example.assetmanagement.domain.DomainRepository
import com.example.assetmanagement.domain.DomainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DomainRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindDomainRepository(impl: DomainRepositoryImpl): DomainRepository
}