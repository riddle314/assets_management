package com.example.repo.database.mechanism.transactions_table

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TransactionDetailsDao {

    @Query("SELECT * FROM transactions_table ORDER BY date DESC")
    fun getAllTransactions(): List<TransactionDetailsEntity>

    @Query("SELECT * FROM transactions_table WHERE transaction_id LIKE :transactionId")
    fun getTransaction(transactionId: Int): TransactionDetailsEntity?

    @Query("SELECT * FROM transactions_table WHERE assets_name LIKE '%' || :query || '%' " +
            "OR quantity LIKE '%' || :query || '%' " +
            "OR price LIKE '%' || :query || '%' " +
            "OR priceCurrency LIKE '%' || :query || '%' " +
            " ORDER BY date DESC")
    fun getTransactionsForQuery(query: String): List<TransactionDetailsEntity>

    @Query("DELETE FROM transactions_table WHERE transaction_id LIKE :transactionId ")
    fun deleteTransaction(transactionId: Int)

    @Insert
    fun addTransaction(transaction: TransactionDetailsEntity)

    @Update
    fun editTransaction(transaction: TransactionDetailsEntity)
}