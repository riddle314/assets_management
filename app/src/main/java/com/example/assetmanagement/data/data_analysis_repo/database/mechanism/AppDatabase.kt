package com.example.assetmanagement.data.data_analysis_repo.database.mechanism

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assetmanagement.data.data_analysis_repo.database.mechanism.transactions_table.TransactionDetailsDao
import com.example.assetmanagement.data.data_analysis_repo.database.mechanism.transactions_table.TransactionDetailsEntity


// bump version number if your schema changes
@Database(entities = [TransactionDetailsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDetailsDao(): TransactionDetailsDao

//    companion object {
//        const val DATABASE_NAME: String = "AppDataBase"

//        @Volatile
//        private var INSTANCE: AppDatabase? = null

//        fun getInstance(context: Context): AppDatabase {
//            synchronized(this) {
//                var instance = INSTANCE
//
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        AppDatabase::class.java,
//                        DATABASE_NAME
//                    )
//                        .fallbackToDestructiveMigration()
//                        .build()
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//    }
}