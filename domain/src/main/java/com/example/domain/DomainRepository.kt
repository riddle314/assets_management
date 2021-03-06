package com.example.domain

import com.example.domain.model.*

interface DomainRepository {

    suspend fun getAllTransactions(): ResponseDomainModel<List<TransactionItemResponseDomainModel>>

    suspend fun getTransactionsForQuery(query: String): ResponseDomainModel<List<TransactionItemResponseDomainModel>>

    suspend fun getTransactionDetails(transactionId: Int): ResponseDomainModel<TransactionDetailsResponseDomainModel?>

    suspend fun addTransaction(addTransactionRequestDomainModel: AddTransactionRequestDomainModel): ResponseDomainModel<String?>

    suspend fun editTransaction(editTransactionRequestDomainModel: EditTransactionRequestDomainModel): ResponseDomainModel<String?>

    suspend fun deleteTransaction(transactionId: Int): ResponseDomainModel<String?>

    suspend fun getAllSearchItems(searchTypeDomain: SearchTypeDomain): ResponseDomainModel<List<SelectionListResultDomainModel>>

    suspend fun getSearchItemsForQuery(
        query: String,
        searchTypeDomain: SearchTypeDomain
    ): ResponseDomainModel<List<SelectionListResultDomainModel>>
}