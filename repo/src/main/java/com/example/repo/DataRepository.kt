package com.example.repo

import com.example.repo.model.*

interface DataRepository {

    suspend fun getAllTransactions(): ResponseDataModel<List<TransactionItemResponseDataModel>>

    suspend fun getTransactionsForQuery(query: String): ResponseDataModel<List<TransactionItemResponseDataModel>>

    suspend fun getTransactionDetails(transactionId: Int): ResponseDataModel<TransactionDetailsResponseDataModel?>

    suspend fun addTransaction(addTransactionRequestDataModel: AddTransactionRequestDataModel): ResponseDataModel<String?>

    suspend fun editTransaction(editTransactionRequestDataModel: EditTransactionRequestDataModel): ResponseDataModel<String?>

    suspend fun deleteTransaction(transactionId: Int): ResponseDataModel<String?>

    suspend fun getAllCurrencies(): ResponseDataModel<List<SelectionListResultDataModel>>

    suspend fun getCurrenciesForQuery(query: String): ResponseDataModel<List<SelectionListResultDataModel>>

    suspend fun getAllCrypto(): ResponseDataModel<List<SelectionListResultDataModel>>

    suspend fun getCryptoForQuery(query: String): ResponseDataModel<List<SelectionListResultDataModel>>

    suspend fun getAllStocks(): ResponseDataModel<List<SelectionListResultDataModel>>

    suspend fun getStocksForQuery(query: String): ResponseDataModel<List<SelectionListResultDataModel>>
}