package com.example.assetmanagement.data.data_analysis_repo.database

import com.example.assetmanagement.data.data_analysis_repo.model.*

interface DatabaseService {

    fun getAllTransactions(): ResponseDataModel<List<TransactionItemResponseDataModel>>

    fun getTransactionsForQuery(query: String): ResponseDataModel<List<TransactionItemResponseDataModel>>

    fun getTransaction(transactionId: Int): ResponseDataModel<TransactionDetailsResponseDataModel?>

    fun deleteTransaction(transactionId: Int): ResponseDataModel<String?>

    fun addTransaction(addTransactionRequestDataModel: AddTransactionRequestDataModel): ResponseDataModel<String?>

    fun editTransaction(editTransactionRequestDataModel: EditTransactionRequestDataModel): ResponseDataModel<String?>

}