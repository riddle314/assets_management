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

    companion object {
        private const val ERROR_MESSAGE = "error"
    }

    override suspend fun getAllTransactions(): ResponseDataModel<List<TransactionItemResponseDataModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel<List<TransactionItemResponseDataModel>>(
                arrayListOf(), false,
                ERROR_MESSAGE
            )
        }
    }

    override suspend fun getTransactionsForQuery(query: String): ResponseDataModel<List<TransactionItemResponseDataModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel<List<TransactionItemResponseDataModel>>(
                arrayListOf(), false,
                ERROR_MESSAGE
            )
        }
    }

    override suspend fun getTransactionDetails(transactionId: Int): ResponseDataModel<TransactionDetailsResponseDataModel?> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel(null, false, ERROR_MESSAGE)

        }
    }

    override suspend fun addTransaction(addTransactionRequestDataModel: AddTransactionRequestDataModel): ResponseDataModel<String?> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel(null, false, ERROR_MESSAGE)
        }
    }

    override suspend fun editTransaction(editTransactionRequestDataModel: EditTransactionRequestDataModel): ResponseDataModel<String?> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel(null, false, ERROR_MESSAGE)
        }

    }

    override suspend fun deleteTransaction(transactionId: Int): ResponseDataModel<String?> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel(null, false, ERROR_MESSAGE)
        }
    }

    override suspend fun getAllCurrencies(): ResponseDataModel<List<SelectionListResultDataModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel<List<SelectionListResultDataModel>>(
                arrayListOf(), false,
                ERROR_MESSAGE
            )
        }
    }

    override suspend fun getCurrenciesForQuery(query: String): ResponseDataModel<List<SelectionListResultDataModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel<List<SelectionListResultDataModel>>(
                arrayListOf(), false,
                ERROR_MESSAGE
            )
        }
    }

    override suspend fun getAllCrypto(): ResponseDataModel<List<SelectionListResultDataModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel<List<SelectionListResultDataModel>>(
                arrayListOf(), false,
                ERROR_MESSAGE
            )
        }
    }

    override suspend fun getCryptoForQuery(query: String): ResponseDataModel<List<SelectionListResultDataModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel<List<SelectionListResultDataModel>>(
                arrayListOf(),
                false,
                ERROR_MESSAGE
            )
        }
    }

    override suspend fun getAllStocks(): ResponseDataModel<List<SelectionListResultDataModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel<List<SelectionListResultDataModel>>(
                arrayListOf(), false,
                ERROR_MESSAGE
            )
        }
    }

    override suspend fun getStocksForQuery(query: String): ResponseDataModel<List<SelectionListResultDataModel>> {
        return changeExecutionThread(Dispatchers.IO) {
            ResponseDataModel<List<SelectionListResultDataModel>>(
                arrayListOf(), false,
                ERROR_MESSAGE
            )
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