package com.example.domain

import com.example.domain.model.*

interface DataRepository {

    suspend fun getAllTransactions(): ResponseDomainModel<List<TransactionItemResponseDomainModel>>

    suspend fun getTransactionsForQuery(query: String): ResponseDomainModel<List<TransactionItemResponseDomainModel>>

    suspend fun getTransactionDetails(transactionId: Int): ResponseDomainModel<TransactionDetailsResponseDomainModel?>

    suspend fun addTransaction(addTransactionRequestDomainModel: AddTransactionRequestDomainModel): ResponseDomainModel<String?>

    suspend fun editTransaction(editTransactionRequestDomainModel: EditTransactionRequestDomainModel): ResponseDomainModel<String?>

    suspend fun deleteTransaction(transactionId: Int): ResponseDomainModel<String?>

    suspend fun getAllCurrencies(): ResponseDomainModel<List<SelectionListResponseDomainModel>>

    suspend fun getCurrenciesForQuery(query: String): ResponseDomainModel<List<SelectionListResponseDomainModel>>

    suspend fun getAllCrypto(): ResponseDomainModel<List<SelectionListResponseDomainModel>>

    suspend fun getCryptoForQuery(query: String): ResponseDomainModel<List<SelectionListResponseDomainModel>>

    suspend fun getAllStocks(): ResponseDomainModel<List<SelectionListResponseDomainModel>>

    suspend fun getStocksForQuery(query: String): ResponseDomainModel<List<SelectionListResponseDomainModel>>
}