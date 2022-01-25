package com.example.repo.database.mechanism

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.repo.database.mechanism.dao.CurrenciesDao
import com.example.repo.database.mechanism.tables.CurrencyEntity
import com.example.repo.database.mechanism.dao.TransactionsDao
import com.example.repo.database.mechanism.tables.TransactionDetailsEntity


// bump version number if your schema changes
@Database(entities = [TransactionDetailsEntity::class, CurrencyEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDetailsDao(): TransactionsDao

    abstract fun currenciesDao(): CurrenciesDao

}