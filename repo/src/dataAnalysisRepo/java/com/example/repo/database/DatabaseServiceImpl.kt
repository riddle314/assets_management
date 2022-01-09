package com.example.repo.database

import android.content.Context
import com.example.repo.R
import com.example.repo.database.mechanism.AppDatabase
import com.example.repo.model.*
import com.example.repo.transformers.EntityToDataTransformers
import com.example.repo.transformers.DataToEntityTransformers
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class DatabaseServiceImpl @Inject constructor(@ApplicationContext private val context: Context) :
    DatabaseService {

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface DatabaseServiceImplEntryPoint {
        fun getDatabase(): AppDatabase
    }

    private var database: AppDatabase = EntryPointAccessors.fromApplication(
        context,
        DatabaseServiceImplEntryPoint::class.java
    ).getDatabase()

    override fun getAllTransactions(): ResponseDataModel<List<TransactionItemResponseDataModel>> {
        return try {
            val result = EntityToDataTransformers.transform(
                database.transactionDetailsDao().getAllTransactions()
            )
            ResponseDataModel(result, true, null)
        } catch (e: Exception) {
            ResponseDataModel(ArrayList(0), false, getError(e, context))
        }
    }

    override fun getTransactionsForQuery(query: String): ResponseDataModel<List<TransactionItemResponseDataModel>> {
        return try {
            val result = EntityToDataTransformers.transform(
                database.transactionDetailsDao().getTransactionsForQuery(query)
            )
            ResponseDataModel(result, true, null)
        } catch (e: Exception) {
            ResponseDataModel(ArrayList(0), false, getError(e, context))
        }
    }

    override fun getTransaction(transactionId: Int): ResponseDataModel<TransactionDetailsResponseDataModel?> {
        return try {
            val result = EntityToDataTransformers.transformToTransactionDetailsDomainModel(
                database.transactionDetailsDao().getTransaction(transactionId)
            )
            ResponseDataModel(
                result, true, null
            )
        } catch (e: Exception) {
            ResponseDataModel(null, false, getError(e, context))
        }
    }

    override fun addTransaction(addTransactionRequestDataModel: AddTransactionRequestDataModel): ResponseDataModel<String?> {
        return try {
            database.transactionDetailsDao().addTransaction(
                DataToEntityTransformers.transformToTransactionDetailsEntity(addTransactionRequestDataModel)
            )
            ResponseDataModel(context.getString(R.string.success), true, null)
        } catch (e: Exception) {
            ResponseDataModel(null, false, getError(e, context))
        }
    }

    override fun editTransaction(editTransactionRequestDataModel: EditTransactionRequestDataModel): ResponseDataModel<String?> {
        return try {
            database.transactionDetailsDao().editTransaction(
                DataToEntityTransformers.transformToTransactionDetailsEntity(editTransactionRequestDataModel)
            )
            ResponseDataModel(context.getString(R.string.success), true, null)
        } catch (e: Exception) {
            ResponseDataModel(null, false, getError(e, context))
        }
    }

    override fun deleteTransaction(transactionId: Int): ResponseDataModel<String?> {
        return try {
            database.transactionDetailsDao().deleteTransaction(transactionId)
            ResponseDataModel(context.getString(R.string.success), true, null)
        } catch (e: Exception) {
            ResponseDataModel(null, false, getError(e, context))
        }
    }

    fun getError(e: Exception, context: Context) =
        e.message ?: context.getString(R.string.general_error)


    inline fun <T> makeActionWithErrorHandling(
        context: Context,
        errorResponseData: T?,
        action: () -> ResponseDataModel<T?>
    ): ResponseDataModel<T?> {
        return try {
            action()
        } catch (e: Exception) {
            ResponseDataModel(errorResponseData, false, getError(e, context))
        }
    }
}