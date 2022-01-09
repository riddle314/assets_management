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
            ResponseDataModel(TestData.getTestData(), true, null),
            demoTimeDelay
        )
    }

    override suspend fun getTransactionsForQuery(query: String): ResponseDataModel<List<TransactionItemResponseDataModel>> {
        return delayExecution(
            ResponseDataModel(TestData.getTestDataForSearchQuery(), true, null),
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