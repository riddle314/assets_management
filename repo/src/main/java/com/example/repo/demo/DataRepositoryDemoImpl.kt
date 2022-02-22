package com.example.repo.demo

import android.content.Context
import com.example.domain.model.*
import com.example.repo.R
import com.example.repo.transformers.DataToDomainTransformers
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepositoryDemoImpl @Inject constructor(@ApplicationContext private val context: Context) :
    com.example.domain.DataRepository {

    private val demoTimeDelay: Long =
        context.resources.getInteger(R.integer.demo_time_delay_ms).toLong()

    override suspend fun getAllTransactions(): ResponseDomainModel<List<TransactionItemResponseDomainModel>> {
        return delayExecution(
            ResponseDomainModel(
                DataToDomainTransformers.transactionItemResponseListTransformer(
                    TestData.getTransactionsTestData()
                ), true, null
            ),
            demoTimeDelay
        )
    }

    override suspend fun getTransactionsForQuery(query: String): ResponseDomainModel<List<TransactionItemResponseDomainModel>> {
        return delayExecution(
            ResponseDomainModel(
                DataToDomainTransformers.transactionItemResponseListTransformer(
                    TestData.getTransactionsTestDataForSearchQuery(query)
                ), true, null
            ),
            demoTimeDelay
        )
    }

    override suspend fun getTransactionDetails(transactionId: Int): ResponseDomainModel<TransactionDetailsResponseDomainModel?> {
        return delayExecution(
            ResponseDomainModel(
                DataToDomainTransformers.transactionDetailsResponseTransformer(
                    TestData.getTransactionDetailsTestData(
                        transactionId
                    )
                ), true, null
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

    override suspend fun getAllCurrencies(): ResponseDomainModel<List<SelectionListResponseDomainModel>> {
        return delayExecution(
            ResponseDomainModel(
                DataToDomainTransformers.selectionListResultListTransformer(TestData.getCurrenciesTestData()),
                true,
                null
            ),
            demoTimeDelay
        )
    }

    override suspend fun getCurrenciesForQuery(query: String): ResponseDomainModel<List<SelectionListResponseDomainModel>> {
        return delayExecution(
            ResponseDomainModel(
                DataToDomainTransformers.selectionListResultListTransformer(
                    TestData.getCurrenciesTestDataForSearchQuery(
                        query
                    )
                ), true, null
            ),
            demoTimeDelay
        )
    }

    override suspend fun getAllCrypto(): ResponseDomainModel<List<SelectionListResponseDomainModel>> {
        return delayExecution(
            ResponseDomainModel(
                DataToDomainTransformers.selectionListResultListTransformer(TestData.getCryptoTestData()),
                true,
                null
            ),
            demoTimeDelay
        )
    }

    override suspend fun getCryptoForQuery(query: String): ResponseDomainModel<List<SelectionListResponseDomainModel>> {
        return delayExecution(
            ResponseDomainModel(
                DataToDomainTransformers.selectionListResultListTransformer(
                    TestData.getCryptoTestDataForSearchQuery(
                        query
                    )
                ), true, null
            ),
            demoTimeDelay
        )
    }

    override suspend fun getAllStocks(): ResponseDomainModel<List<SelectionListResponseDomainModel>> {
        return delayExecution(
            ResponseDomainModel(
                DataToDomainTransformers.selectionListResultListTransformer(TestData.getStocksTestData()),
                true,
                null
            ),
            demoTimeDelay
        )
    }

    override suspend fun getStocksForQuery(query: String): ResponseDomainModel<List<SelectionListResponseDomainModel>> {
        return delayExecution(
            ResponseDomainModel(
                DataToDomainTransformers.selectionListResultListTransformer(
                    TestData.getStocksTestDataForSearchQuery(
                        query
                    )
                ), true, null
            ),
            demoTimeDelay
        )
    }

    // delay suspend function to a different thread than main thread
    private suspend fun <T> delayExecution(
        responseModel: ResponseDomainModel<T>,
        timeDelay: Long
    ): ResponseDomainModel<T> {
        return withContext(Dispatchers.IO) {
            delay(timeDelay)
            responseModel
        }
    }
}