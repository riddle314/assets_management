package com.example.repo.di

import android.content.Context
import com.example.repo.DataRepository
import com.example.repo.DataRepositoryImpl
import com.example.repo.R
import com.example.repo.demo.DataRepositoryDemoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataRepositoryModule {

    @Provides
    @Singleton
    fun provideDataRepository(@ApplicationContext context: Context): DataRepository {
        val isDemo: Boolean = context.resources.getBoolean(R.bool.is_demo_enabled)
        return if (isDemo) {
            DataRepositoryDemoImpl(context)
        } else {
            DataRepositoryImpl(context)
        }
    }


}