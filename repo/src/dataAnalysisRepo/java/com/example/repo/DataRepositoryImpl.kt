package com.example.repo

import android.content.Context
import com.example.domain.DataRepository
import com.example.domain.model.*
import com.example.repo.database.DatabaseService
import com.example.repo.demo.TestData
import com.example.repo.model.ResponseDataModel
import com.example.repo.model.SelectionListResultDataModel
import com.example.repo.network.NetworkService
import com.example.repo.transformers.DataToDomainTransformers
import com.example.repo.transformers.DomainToDataTransformers
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

    // the DataRepository is provided as singleton per Application context,
    // so as long the app running the boolean below is cached.
    private var currenciesAreCached: Boolean = false

//    private var analysisService: AnalysisService

    override suspend fun getAllTransactions(): ResponseDomainModel<List<TransactionItemResponseDomainModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            val result = dataBaseService.getAllTransactions()
            ResponseDomainModel(
                DataToDomainTransformers.transactionItemResponseListTransformer(result.responseData),
                result.isSuccess, result.errorMessage
            )
        }
    }

    override suspend fun getTransactionsForQuery(query: String): ResponseDomainModel<List<TransactionItemResponseDomainModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            val result = dataBaseService.getTransactionsForQuery(query)
            ResponseDomainModel(
                DataToDomainTransformers.transactionItemResponseListTransformer(result.responseData),
                result.isSuccess, result.errorMessage
            )
        }
    }

    override suspend fun getTransactionDetails(transactionId: Int): ResponseDomainModel<TransactionDetailsResponseDomainModel?> {
        return changeExecutionThread(Dispatchers.IO) {
            val result = dataBaseService.getTransaction(transactionId)
            ResponseDomainModel(
                DataToDomainTransformers.transactionDetailsResponseTransformer(result.responseData),
                result.isSuccess, result.errorMessage
            )

        }
    }

    override suspend fun addTransaction(addTransactionRequestDomainModel: AddTransactionRequestDomainModel): ResponseDomainModel<String?> {
        return changeExecutionThread(Dispatchers.IO) {
            val result = dataBaseService.addTransaction(
                DomainToDataTransformers.addTransactionRequestTransformer(
                    addTransactionRequestDomainModel
                )
            )

            ResponseDomainModel(
                result.responseData,
                result.isSuccess, result.errorMessage
            )
        }
    }

    override suspend fun editTransaction(editTransactionRequestDomainModel: EditTransactionRequestDomainModel): ResponseDomainModel<String?> {
        return changeExecutionThread(Dispatchers.IO) {
            val result = dataBaseService.editTransaction(
                DomainToDataTransformers.editTransactionRequestTransformer(
                    editTransactionRequestDomainModel
                )
            )

            ResponseDomainModel(
                result.responseData,
                result.isSuccess, result.errorMessage
            )
        }
    }

    override suspend fun deleteTransaction(transactionId: Int): ResponseDomainModel<String?> {
        return changeExecutionThread(Dispatchers.IO) {
            val result = dataBaseService.deleteTransaction(transactionId)

            ResponseDomainModel(
                result.responseData,
                result.isSuccess, result.errorMessage
            )
        }
    }

    override suspend fun getAllCurrencies(): ResponseDomainModel<List<SelectionListResponseDomainModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            val response: ResponseDataModel<List<SelectionListResultDataModel>>
            // if we have already make the rest call for the currencies, while the app is running,
            // then we just retrieve them from the database
            if (!currenciesAreCached) {
                response = networkService.getAllCurrencies()
                if (response.isSuccess) {
                    val databaseResponse =
                        dataBaseService.insertAllCurrencies(response.responseData)
                    currenciesAreCached = databaseResponse.isSuccess
                    response.isSuccess = databaseResponse.isSuccess
                    response.errorMessage = databaseResponse.errorMessage
                }
            } else {
                response = dataBaseService.getAllCurrencies()
            }
            ResponseDomainModel(
                DataToDomainTransformers.selectionListResultListTransformer(response.responseData),
                response.isSuccess, response.errorMessage
            )
        }
    }

    override suspend fun getCurrenciesForQuery(query: String): ResponseDomainModel<List<SelectionListResponseDomainModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            val result = dataBaseService.getCurrenciesForQuery(query)

            ResponseDomainModel(
                DataToDomainTransformers.selectionListResultListTransformer(result.responseData),
                result.isSuccess, result.errorMessage
            )
        }
    }

    override suspend fun getAllCrypto(): ResponseDomainModel<List<SelectionListResponseDomainModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDomainModel(
                DataToDomainTransformers.selectionListResultListTransformer(TestData.getCryptoTestData()),
                true,
                null
            )
        }
    }

    override suspend fun getCryptoForQuery(query: String): ResponseDomainModel<List<SelectionListResponseDomainModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDomainModel(
                DataToDomainTransformers.selectionListResultListTransformer(
                    TestData.getCryptoTestDataForSearchQuery(
                        query
                    )
                ), true, null
            )
        }
    }

    override suspend fun getAllStocks(): ResponseDomainModel<List<SelectionListResponseDomainModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDomainModel(
                DataToDomainTransformers.selectionListResultListTransformer(TestData.getStocksTestData()),
                true,
                null
            )
        }
    }

    override suspend fun getStocksForQuery(query: String): ResponseDomainModel<List<SelectionListResponseDomainModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDomainModel(
                DataToDomainTransformers.selectionListResultListTransformer(
                    TestData.getStocksTestDataForSearchQuery(
                        query
                    )
                ), true, null
            )
        }
    }

    // suspend inline function to change thread and execute
    private suspend inline fun <T> changeExecutionThread(
        dispatcher: CoroutineDispatcher,
        crossinline action: () -> ResponseDomainModel<T>
    ): ResponseDomainModel<T> {
        return withContext(dispatcher) {
            action()
        }
    }
}