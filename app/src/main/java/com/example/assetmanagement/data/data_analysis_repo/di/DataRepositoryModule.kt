package com.example.assetmanagement.data.data_analysis_repo.di

import com.example.assetmanagement.data.DataRepository
import com.example.assetmanagement.data.data_analysis_repo.DataRepositoryImpl
import com.example.assetmanagement.data.demo_repo.DataRepositoryDemoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataRepositoryModule {

    @Singleton
    @DataAnalysisRepository
    @Binds
    abstract fun bindDataAnalysisRepository(impl: DataRepositoryImpl): DataRepository

    @Singleton
    @DemoRepository
    @Binds
    abstract fun bindDemoRepository(impl: DataRepositoryDemoImpl): DataRepository
}