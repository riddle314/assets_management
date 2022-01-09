package com.example.repo.database.di

import com.example.repo.database.DatabaseService
import com.example.repo.database.DatabaseServiceImpl
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

