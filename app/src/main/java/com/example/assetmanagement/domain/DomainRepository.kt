package com.example.assetmanagement.domain

import com.example.assetmanagement.domain.model.*

interface DomainRepository {

    suspend fun getAllTransactions(): ResponseDomainModel<List<TransactionItemResponseDomainModel>>

    suspend fun getTransactionsForQuery(query: String): ResponseDomainModel<List<TransactionItemResponseDomainModel>>

    suspend fun getTransactionDetails(transactionId: Int): ResponseDomainModel<TransactionDetailsResponseDomainModel?>

    suspend fun addTransaction(addTransactionRequestDomainModel: AddTransactionRequestDomainModel): ResponseDomainModel<String?>

    suspend fun editTransaction(editTransactionRequestDomainModel: EditTransactionRequestDomainModel): ResponseDomainModel<String?>

    suspend fun deleteTransaction(transactionId: Int): ResponseDomainModel<String?>
}