package com.example.repo

import android.content.Context
import com.example.repo.database.DatabaseService
import com.example.repo.model.*
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
    }

    private val entryPoint = EntryPointAccessors.fromApplication(
        context,
        RepositoryImplEntryPoint::class.java
    )

    private var dataBaseService: DatabaseService = entryPoint.getDatabaseService()

//    private var networkService: NetworkService
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