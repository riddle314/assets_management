package com.example.assetmanagement.domain.di

import com.example.assetmanagement.data.data_analysis_repo.RepositoryImpl
import com.example.assetmanagement.data.demo_repo.RepositoryDemoImpl
import com.example.assetmanagement.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @DataAnalysisRepository
    @Binds
    abstract fun bindDataAnalysisRepository(impl: RepositoryImpl): Repository

    @Singleton
    @DemoRepository
    @Binds
    abstract fun bindDemoRepository(impl: RepositoryDemoImpl): Repository

//    can I use @Provide and @Bind to the same module?
}