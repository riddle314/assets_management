package com.example.assetmanagement.data.data_analysis_repo.database.mechanism.di

import android.content.Context
import androidx.room.Room
import com.example.assetmanagement.R
import com.example.assetmanagement.data.data_analysis_repo.database.mechanism.AppDatabase
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
        // do I need synchronized?
//        return synchronized(this) {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            context.getString(R.string.database_name)
        ).fallbackToDestructiveMigration()
            .build()
//        }
    }

}