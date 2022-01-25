package com.example.repo.database.mechanism.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A data class for the Currencies Table
 */
@Entity(tableName = "currencies_table")
data class CurrencyEntity(
    @PrimaryKey @ColumnInfo(name = "currency") var currency: String,
    @ColumnInfo(name = "description") var description: String
)
