package com.example.assetmanagement.data.data_analysis_repo.database.mechanism.transactions_table

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TransactionDetailsDao {

    @Query("SELECT * FROM transactions_table")
    fun getAllTransactions(): List<TransactionDetailsEntity>

    @Query("SELECT * FROM transactions_table WHERE transaction_id LIKE :transactionId ")
    fun getTransaction(transactionId: Int): TransactionDetailsEntity?

    @Query("SELECT * FROM transactions_table WHERE assets_name OR quantity OR price OR priceCurrency OR date OR assetType OR transactionType LIKE '%' || :query || '%'")
    fun getTransactionsForQuery(query: String): List<TransactionDetailsEntity>

    @Query("DELETE FROM transactions_table WHERE transaction_id LIKE :transactionId ")
    fun deleteTransaction(transactionId: Int)

    @Insert
    fun addTransaction(transaction: TransactionDetailsEntity)

    @Update
    fun editTransaction(transaction: TransactionDetailsEntity)
}