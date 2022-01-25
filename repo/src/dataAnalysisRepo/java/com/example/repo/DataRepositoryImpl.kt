package com.example.repo

import android.content.Context
import com.example.repo.database.DatabaseService
import com.example.repo.demo.TestData
import com.example.repo.model.*
import com.example.repo.network.NetworkService
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(@ApplicationContext context: Context) :
    DataRepository {

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface RepositoryImplEntryPoint {
        fun getDatabaseService(): DatabaseService
        fun getNetworkService(): NetworkService
    }

    private val entryPoint = EntryPointAccessors.fromApplication(
        context,
        RepositoryImplEntryPoint::class.java
    )

    private var dataBaseService: DatabaseService = entryPoint.getDatabaseService()

    private var networkService: NetworkService = entryPoint.getNetworkService()

    private var currenciesAreAlreadedLoaded: Boolean = false

//    private var analysisService: AnalysisService

    override suspend fun getAllTransactions(): ResponseDataModel<List<TransactionItemResponseDataModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            dataBaseService.getAllTransactions()
        }
    }

    override suspend fun getTransactionsForQuery(query: String): ResponseDataModel<List<TransactionItemResponseDataModel>> {
        return changeExecutionThread(Dispatchers.IO) { dataBaseService.getTransactionsForQuery(query) }
    }

    override suspend fun getTransactionDetails(transactionId: Int): ResponseDataModel<TransactionDetailsResponseDataModel?> {
        return changeExecutionThread(Dispatchers.IO) {
            dataBaseService.getTransaction(transactionId)
        }
    }

    override suspend fun addTransaction(addTransactionRequestDataModel: AddTransactionRequestDataModel): ResponseDataModel<String?> {
        return changeExecutionThread(Dispatchers.IO) {
            dataBaseService.addTransaction(
                addTransactionRequestDataModel
            )
        }
    }

    override suspend fun editTransaction(editTransactionRequestDataModel: EditTransactionRequestDataModel): ResponseDataModel<String?> {
        return changeExecutionThread(Dispatchers.IO) {
            dataBaseService.editTransaction(editTransactionRequestDataModel)
        }

    }

    override suspend fun deleteTransaction(transactionId: Int): ResponseDataModel<String?> {
        return changeExecutionThread(Dispatchers.IO) {
            dataBaseService.deleteTransaction(transactionId)
        }
    }

    override suspend fun getAllCurrencies(): ResponseDataModel<List<SelectionListResultDataModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            // if we have already make the rest call for the currencies, while the app is running,
            // then we just retrieve them from the database
            if (!currenciesAreAlreadedLoaded) {
                val restResponse = networkService.getAllCurrencies()
                if (restResponse.isSuccess) {
                    val databaseResponse =
                        dataBaseService.insertAllCurrencies(restResponse.responseData)
                    currenciesAreAlreadedLoaded = databaseResponse.isSuccess
                    restResponse.isSuccess = databaseResponse.isSuccess
                    restResponse.errorMessage = databaseResponse.errorMessage
                }
                restResponse
            } else {
                dataBaseService.getAllCurrencies()
            }
        }
    }

    override suspend fun getCurrenciesForQuery(query: String): ResponseDataModel<List<SelectionListResultDataModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            dataBaseService.getCurrenciesForQuery(query)
        }
    }

    override suspend fun getAllCrypto(): ResponseDataModel<List<SelectionListResultDataModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel(TestData.getCryptoTestData(), true, null)
        }
    }

    override suspend fun getCryptoForQuery(query: String): ResponseDataModel<List<SelectionListResultDataModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel(TestData.getCryptoTestDataForSearchQuery(query), true, null)
        }
    }

    override suspend fun getAllStocks(): ResponseDataModel<List<SelectionListResultDataModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel(TestData.getStocksTestData(), true, null)
        }
    }

    override suspend fun getStocksForQuery(query: String): ResponseDataModel<List<SelectionListResultDataModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel(TestData.getStocksTestDataForSearchQuery(query), true, null)
        }
    }

    // suspend inline function to change thread and execute
    private suspend inline fun <T> changeExecutionThread(
        dispatcher: CoroutineDispatcher,
        crossinline action: () -> ResponseDataModel<T>
    ): ResponseDataModel<T> {
        return withContext(dispatcher) {
            action()
        }
    }
}