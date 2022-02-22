package com.example.repo

import android.content.Context
import com.example.domain.DataRepository
import com.example.domain.model.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) :
    DataRepository {

    override suspend fun getAllTransactions(): ResponseDomainModel<List<TransactionItemResponseDomainModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            errorResponse(arrayListOf())
        }
    }

    override suspend fun getTransactionsForQuery(query: String): ResponseDomainModel<List<TransactionItemResponseDomainModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            errorResponse(arrayListOf())
        }
    }

    override suspend fun getTransactionDetails(transactionId: Int): ResponseDomainModel<TransactionDetailsResponseDomainModel?> {
        return changeExecutionThread(Dispatchers.IO) {
            errorResponse(null)
        }
    }

    override suspend fun addTransaction(addTransactionRequestDomainModel: AddTransactionRequestDomainModel): ResponseDomainModel<String?> {
        return changeExecutionThread(Dispatchers.IO) {
            errorResponse(null)
        }
    }

    override suspend fun editTransaction(editTransactionRequestDomainModel: EditTransactionRequestDomainModel): ResponseDomainModel<String?> {
        return changeExecutionThread(Dispatchers.IO) {
            errorResponse(null)
        }

    }

    override suspend fun deleteTransaction(transactionId: Int): ResponseDomainModel<String?> {
        return changeExecutionThread(Dispatchers.IO) {
            errorResponse(null)
        }
    }

    override suspend fun getAllCurrencies(): ResponseDomainModel<List<SelectionListResponseDomainModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            errorResponse(arrayListOf())
        }
    }

    override suspend fun getCurrenciesForQuery(query: String): ResponseDomainModel<List<SelectionListResponseDomainModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            errorResponse(arrayListOf())
        }
    }

    override suspend fun getAllCrypto(): ResponseDomainModel<List<SelectionListResponseDomainModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            errorResponse(arrayListOf())
        }
    }

    override suspend fun getCryptoForQuery(query: String): ResponseDomainModel<List<SelectionListResponseDomainModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            errorResponse(arrayListOf())
        }
    }

    override suspend fun getAllStocks(): ResponseDomainModel<List<SelectionListResponseDomainModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            errorResponse(arrayListOf())
        }
    }

    override suspend fun getStocksForQuery(query: String): ResponseDomainModel<List<SelectionListResponseDomainModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            errorResponse(arrayListOf())
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

    private fun <T> errorResponse(data: T): ResponseDomainModel<T> {
        return ResponseDomainModel(data, false, context.getString(R.string.general_error))
    }
}