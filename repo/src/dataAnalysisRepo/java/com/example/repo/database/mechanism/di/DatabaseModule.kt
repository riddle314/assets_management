package com.example.repo.database.mechanism.di

import android.content.Context
import androidx.room.Room
import com.example.repo.R
import com.example.repo.database.mechanism.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            context.getString(R.string.database_name),
        ).fallbackToDestructiveMigration()
            .build()
    }

}