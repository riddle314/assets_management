package com.example.assetmanagement.data.data_analysis_repo.database.di

import com.example.assetmanagement.data.data_analysis_repo.database.DatabaseService
import com.example.assetmanagement.data.data_analysis_repo.database.DatabaseServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DatabaseServiceModule {

    @Singleton
    @Binds
    abstract fun bindDatabaseService(impl: DatabaseServiceImpl): DatabaseService

}

