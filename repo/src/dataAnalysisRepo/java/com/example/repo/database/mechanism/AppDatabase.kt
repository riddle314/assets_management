package com.example.repo.database.mechanism

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.repo.database.mechanism.transactions_table.TransactionDetailsDao
import com.example.repo.database.mechanism.transactions_table.TransactionDetailsEntity


// bump version number if your schema changes
@Database(entities = [TransactionDetailsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDetailsDao(): TransactionDetailsDao

}