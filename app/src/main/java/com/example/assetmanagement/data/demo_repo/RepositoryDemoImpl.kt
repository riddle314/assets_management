package com.example.assetmanagement.data.demo_repo

import android.content.Context
import com.example.assetmanagement.R
import com.example.assetmanagement.domain.Repository
import com.example.assetmanagement.domain.model.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryDemoImpl @Inject constructor(@ApplicationContext private val context: Context) :
    Repository {

    private val demoTimeDelay: Long =
        context.resources.getInteger(R.integer.demo_time_delay_ms).toLong()

    override suspend fun getAllTransactions(): ResponseDomainModel<List<TransactionItemResponseDomainModel>> {
        return delayExecution(
            ResponseDomainModel(TestData.getTestData(), true, null),
            demoTimeDelay
        )
    }

    override suspend fun getTransactionsForQuery(query: String): ResponseDomainModel<List<TransactionItemResponseDomainModel>> {
        return delayExecution(
            ResponseDomainModel(TestData.getTestDataForSearchQuery(), true, null),
            demoTimeDelay
        )
    }

    override suspend fun getTransactionDetails(transactionId: Int): ResponseDomainModel<TransactionDetailsResponseDomainModel?> {
        return delayExecution(
            ResponseDomainModel(
                TestData.getTransactionDetailsTestData(transactionId), true, null
            ), demoTimeDelay
        )
    }

    override suspend fun addTransaction(addTransactionRequestDomainModel: AddTransactionRequestDomainModel): ResponseDomainModel<String?> {
        return delayExecution(ResponseDomainModel<String?>(null, true, null), demoTimeDelay)
    }

    override suspend fun editTransaction(editTransactionRequestDomainModel: EditTransactionRequestDomainModel): ResponseDomainModel<String?> {
        return delayExecution(ResponseDomainModel<String?>(null, true, null), demoTimeDelay)
    }

    override suspend fun deleteTransaction(transactionId: Int): ResponseDomainModel<String?> {
        return delayExecution(ResponseDomainModel<String?>(null, true, null), demoTimeDelay)
    }

    // delay suspend function to a different thread than main thread
    private suspend fun <T> delayExecution(
        responseDomainModel: ResponseDomainModel<T>,
        timeDelay: Long
    ): ResponseDomainModel<T> {
        return withContext(Dispatchers.IO) {
            delay(timeDelay)
            responseDomainModel
        }
    }
}