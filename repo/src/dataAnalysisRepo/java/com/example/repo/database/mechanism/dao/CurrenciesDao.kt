package com.example.repo.database.mechanism.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.repo.database.mechanism.tables.CurrencyEntity
import com.example.repo.database.mechanism.tables.TransactionDetailsEntity

@Dao
interface CurrenciesDao {

    @Query("SELECT * FROM currencies_table")
    fun getAllCurrencies(): List<CurrencyEntity>

    @Query("SELECT * FROM currencies_table WHERE currency LIKE :currencySymbol")
    fun getCurrency(currencySymbol: String): CurrencyEntity?

    @Query(
        "SELECT * FROM currencies_table WHERE currency LIKE '%' || :query || '%' " +
                "OR description LIKE '%' || :query || '%' "
    )
    fun getCurrenciesForQuery(query: String): List<CurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCurrencies(currencies: List<CurrencyEntity>)
}