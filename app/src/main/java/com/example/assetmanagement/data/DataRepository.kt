package com.example.assetmanagement.data

import com.example.assetmanagement.data.data_analysis_repo.model.*

interface DataRepository {

    suspend fun getAllTransactions(): ResponseDataModel<List<TransactionItemResponseDataModel>>

    suspend fun getTransactionsForQuery(query: String): ResponseDataModel<List<TransactionItemResponseDataModel>>

    suspend fun getTransactionDetails(transactionId: Int): ResponseDataModel<TransactionDetailsResponseDataModel?>

    suspend fun addTransaction(addTransactionRequestDataModel: AddTransactionRequestDataModel): ResponseDataModel<String?>

    suspend fun editTransaction(editTransactionRequestDataModel: EditTransactionRequestDataModel): ResponseDataModel<String?>

    suspend fun deleteTransaction(transactionId: Int): ResponseDataModel<String?>
}