package com.example.repo

import android.content.Context
import com.example.repo.model.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(@ApplicationContext context: Context) :
    DataRepository {


    override suspend fun getAllTransactions(): ResponseDataModel<List<TransactionItemResponseDataModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel<List<TransactionItemResponseDataModel>>(arrayListOf(), false, "error")
        }
    }

    override suspend fun getTransactionsForQuery(query: String): ResponseDataModel<List<TransactionItemResponseDataModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel<List<TransactionItemResponseDataModel>>(arrayListOf(), false, "error")
        }
    }

    override suspend fun getTransactionDetails(transactionId: Int): ResponseDataModel<TransactionDetailsResponseDataModel?> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel(null, false, "error")

        }
    }

    override suspend fun addTransaction(addTransactionRequestDataModel: AddTransactionRequestDataModel): ResponseDataModel<String?> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel(null, false, "error")
        }
    }

    override suspend fun editTransaction(editTransactionRequestDataModel: EditTransactionRequestDataModel): ResponseDataModel<String?> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel(null, false, "error")
        }

    }

    override suspend fun deleteTransaction(transactionId: Int): ResponseDataModel<String?> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel(null, false, "error")
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