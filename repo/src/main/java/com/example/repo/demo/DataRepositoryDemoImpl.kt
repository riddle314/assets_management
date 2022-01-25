package com.example.repo.demo

import android.content.Context
import com.example.repo.DataRepository
import com.example.repo.R
import com.example.repo.model.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepositoryDemoImpl @Inject constructor(@ApplicationContext private val context: Context) :
    DataRepository {

    private val demoTimeDelay: Long =
        context.resources.getInteger(R.integer.demo_time_delay_ms).toLong()

    override suspend fun getAllTransactions(): ResponseDataModel<List<TransactionItemResponseDataModel>> {
        return delayExecution(
            ResponseDataModel(TestData.getTranscationsTestData(), true, null),
            demoTimeDelay
        )
    }

    override suspend fun getTransactionsForQuery(query: String): ResponseDataModel<List<TransactionItemResponseDataModel>> {
        return delayExecution(
            ResponseDataModel(TestData.getTransactionsTestDataForSearchQuery(query), true, null),
            demoTimeDelay
        )
    }

    override suspend fun getTransactionDetails(transactionId: Int): ResponseDataModel<TransactionDetailsResponseDataModel?> {
        return delayExecution(
            ResponseDataModel(
                TestData.getTransactionDetailsTestData(transactionId), true, null
            ), demoTimeDelay
        )
    }

    override suspend fun addTransaction(addTransactionRequestDataModel: AddTransactionRequestDataModel): ResponseDataModel<String?> {
        return delayExecution(ResponseDataModel<String?>(null, true, null), demoTimeDelay)
    }

    override suspend fun editTransaction(editTransactionRequestDataModel: EditTransactionRequestDataModel): ResponseDataModel<String?> {
        return delayExecution(ResponseDataModel<String?>(null, true, null), demoTimeDelay)
    }

    override suspend fun deleteTransaction(transactionId: Int): ResponseDataModel<String?> {
        return delayExecution(ResponseDataModel<String?>(null, true, null), demoTimeDelay)
    }

    override suspend fun getAllCurrencies(): ResponseDataModel<List<SelectionListResultDataModel>> {
        return delayExecution(
            ResponseDataModel(TestData.getCurrenciesTestData(), true, null),
            demoTimeDelay
        )
    }

    override suspend fun getCurrenciesForQuery(query: String): ResponseDataModel<List<SelectionListResultDataModel>> {
        return delayExecution(
            ResponseDataModel(TestData.getCurrenciesTestDataForSearchQuery(query), true, null),
            demoTimeDelay
        )
    }

    override suspend fun getAllCrypto(): ResponseDataModel<List<SelectionListResultDataModel>> {
        return delayExecution(
            ResponseDataModel(TestData.getCryptoTestData(), true, null),
            demoTimeDelay
        )
    }

    override suspend fun getCryptoForQuery(query: String): ResponseDataModel<List<SelectionListResultDataModel>> {
        return delayExecution(
            ResponseDataModel(TestData.getCryptoTestDataForSearchQuery(query), true, null),
            demoTimeDelay
        )
    }

    override suspend fun getAllStocks(): ResponseDataModel<List<SelectionListResultDataModel>> {
        return delayExecution(
            ResponseDataModel(TestData.getStocksTestData(), true, null),
            demoTimeDelay
        )
    }

    override suspend fun getStocksForQuery(query: String): ResponseDataModel<List<SelectionListResultDataModel>> {
        return delayExecution(
            ResponseDataModel(TestData.getStocksTestDataForSearchQuery(query), true, null),
            demoTimeDelay
        )
    }

    // delay suspend function to a different thread than main thread
    private suspend fun <T> delayExecution(
        responseDataModel: ResponseDataModel<T>,
        timeDelay: Long
    ): ResponseDataModel<T> {
        return withContext(Dispatchers.IO) {
            delay(timeDelay)
            responseDataModel
        }
    }
}