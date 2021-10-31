package com.example.assetmanagement.data.data_analysis_repo

import android.content.Context
import com.example.assetmanagement.data.data_analysis_repo.database.DatabaseService
import com.example.assetmanagement.domain.Repository
import com.example.assetmanagement.domain.model.*
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(@ApplicationContext context: Context) : Repository {

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

    override suspend fun getAllTransactions(): ResponseDomainModel<List<TransactionItemResponseDomainModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            dataBaseService.getAllTransactions()
        }
    }

    override suspend fun getTransactionsForQuery(query: String): ResponseDomainModel<List<TransactionItemResponseDomainModel>> {
        return changeExecutionThread(Dispatchers.IO) { dataBaseService.getTransactionsForQuery(query) }
    }

    override suspend fun getTransactionDetails(transactionId: Int): ResponseDomainModel<TransactionDetailsResponseDomainModel?> {
        return changeExecutionThread(Dispatchers.IO) {
            dataBaseService.getTransaction(transactionId)
        }
    }

    override suspend fun addTransaction(addTransactionRequestDomainModel: AddTransactionRequestDomainModel): ResponseDomainModel<String?> {
        return changeExecutionThread(Dispatchers.IO) {
            dataBaseService.addTransaction(
                addTransactionRequestDomainModel
            )
        }
    }

    override suspend fun editTransaction(editTransactionRequestDomainModel: EditTransactionRequestDomainModel): ResponseDomainModel<String?> {
        return changeExecutionThread(Dispatchers.IO) {
            dataBaseService.editTransaction(editTransactionRequestDomainModel)
        }

    }

    override suspend fun deleteTransaction(transactionId: Int): ResponseDomainModel<String?> {
        return changeExecutionThread(Dispatchers.IO) {
            dataBaseService.deleteTransaction(transactionId)
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