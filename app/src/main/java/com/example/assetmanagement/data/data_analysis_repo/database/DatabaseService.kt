package com.example.assetmanagement.data.data_analysis_repo.database

import com.example.assetmanagement.domain.model.*

interface DatabaseService {

    fun getAllTransactions(): ResponseDomainModel<List<TransactionItemResponseDomainModel>>

    fun getTransactionsForQuery(query: String): ResponseDomainModel<List<TransactionItemResponseDomainModel>>

    fun getTransaction(transactionId: Int): ResponseDomainModel<TransactionDetailsResponseDomainModel?>

    fun deleteTransaction(transactionId: Int) : ResponseDomainModel<String?>

    fun addTransaction(addTransactionRequestDomainModel: AddTransactionRequestDomainModel) : ResponseDomainModel<String?>

    fun editTransaction(editTransactionRequestDomainModel: EditTransactionRequestDomainModel): ResponseDomainModel<String?>

}