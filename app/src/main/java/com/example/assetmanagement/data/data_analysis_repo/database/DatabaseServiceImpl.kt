package com.example.assetmanagement.data.data_analysis_repo.database

import android.content.Context
import com.example.assetmanagement.R
import com.example.assetmanagement.data.data_analysis_repo.database.mechanism.AppDatabase
import com.example.assetmanagement.data.data_analysis_repo.transformers.DataTransformersToDomain
import com.example.assetmanagement.data.data_analysis_repo.transformers.DataTransformersToEntity
import com.example.assetmanagement.domain.model.*
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

    override fun getAllTransactions(): ResponseDomainModel<List<TransactionItemResponseDomainModel>> {
        return try {
            val result = DataTransformersToDomain.transform(
                database.transactionDetailsDao().getAllTransactions()
            )
            ResponseDomainModel(result, true, null)
        } catch (e: Exception) {
            ResponseDomainModel(ArrayList(0), false, getError(e, context))
        }
    }

    override fun getTransactionsForQuery(query: String): ResponseDomainModel<List<TransactionItemResponseDomainModel>> {
        return try {
            val result = DataTransformersToDomain.transform(
                database.transactionDetailsDao().getTransactionsForQuery(query)
            )
            ResponseDomainModel(result, true, null)
        } catch (e: Exception) {
            ResponseDomainModel(ArrayList(0), false, getError(e, context))
        }
    }

    override fun getTransaction(transactionId: Int): ResponseDomainModel<TransactionDetailsResponseDomainModel?> {
        return try {
            val result = DataTransformersToDomain.transformToTransactionDetailsDomainModel(
                database.transactionDetailsDao().getTransaction(transactionId)
            )
            ResponseDomainModel(
                result, true, null
            )
        } catch (e: Exception) {
            ResponseDomainModel(null, false, getError(e, context))
        }
    }

    override fun addTransaction(addTransactionRequestDomainModel: AddTransactionRequestDomainModel): ResponseDomainModel<String?> {
        return try {
            database.transactionDetailsDao().addTransaction(
                DataTransformersToEntity.transformToTransactionDetailsEntity(addTransactionRequestDomainModel)
            )
            ResponseDomainModel(context.getString(R.string.success), true, null)
        } catch (e: Exception) {
            ResponseDomainModel(null, false, getError(e, context))
        }
    }

    override fun editTransaction(editTransactionRequestDomainModel: EditTransactionRequestDomainModel): ResponseDomainModel<String?> {
        return try {
            database.transactionDetailsDao().editTransaction(
                DataTransformersToEntity.transformToTransactionDetailsEntity(editTransactionRequestDomainModel)
            )
            ResponseDomainModel(context.getString(R.string.success), true, null)
        } catch (e: Exception) {
            ResponseDomainModel(null, false, getError(e, context))
        }
    }

    override fun deleteTransaction(transactionId: Int): ResponseDomainModel<String?> {
        return try {
            database.transactionDetailsDao().deleteTransaction(transactionId)
            ResponseDomainModel(context.getString(R.string.success), true, null)
        } catch (e: Exception) {
            ResponseDomainModel(null, false, getError(e, context))
        }
    }

    fun getError(e: Exception, context: Context) =
        e.message ?: context.getString(R.string.general_error)


    inline fun <T> makeActionWithErrorHandling(
        context: Context,
        errorResponseData: T?,
        action: () -> ResponseDomainModel<T?>
    ): ResponseDomainModel<T?> {
        return try {
            action()
        } catch (e: Exception) {
            ResponseDomainModel(errorResponseData, false, getError(e, context))
        }
    }
}