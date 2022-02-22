package com.example.domain.di

import com.example.domain.DomainRepository
import com.example.domain.DomainRepositoryImpl
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