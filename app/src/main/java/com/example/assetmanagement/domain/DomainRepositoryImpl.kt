package com.example.assetmanagement.domain

import com.example.assetmanagement.data.DataRepository
import com.example.assetmanagement.data.data_analysis_repo.di.DataAnalysisRepository
import com.example.assetmanagement.data.data_analysis_repo.model.ResponseDataModel
import com.example.assetmanagement.data.data_analysis_repo.model.TransactionDetailsResponseDataModel
import com.example.assetmanagement.data.data_analysis_repo.model.TransactionItemResponseDataModel
import com.example.assetmanagement.domain.model.*
import com.example.assetmanagement.domain.transformers.DataToDomainTransformers
import com.example.assetmanagement.domain.transformers.DomainToDataTransformers
import javax.inject.Inject

class DomainRepositoryImpl @Inject constructor(
    @DataAnalysisRepository var dataRepository: DataRepository
) : DomainRepository {

    override suspend fun getAllTransactions(): ResponseDomainModel<List<TransactionItemResponseDomainModel>> {

        val response: ResponseDataModel<List<TransactionItemResponseDataModel>> =
            dataRepository.getAllTransactions()

        return ResponseDomainModel(
            DataToDomainTransformers.transactionItemResponseListTransformer(response.responseData),
            response.isSuccess,
            response.errorMessage
        )
    }

    override suspend fun getTransactionsForQuery(query: String): ResponseDomainModel<List<TransactionItemResponseDomainModel>> {

        val response: ResponseDataModel<List<TransactionItemResponseDataModel>> =
            dataRepository.getTransactionsForQuery(query)

        return ResponseDomainModel(
            DataToDomainTransformers.transactionItemResponseListTransformer(response.responseData),
            response.isSuccess,
            response.errorMessage
        )
    }

    override suspend fun getTransactionDetails(transactionId: Int): ResponseDomainModel<TransactionDetailsResponseDomainModel?> {

        val response: ResponseDataModel<TransactionDetailsResponseDataModel?> =
            dataRepository.getTransactionDetails(transactionId)

        return ResponseDomainModel(
            DataToDomainTransformers.transactionDetailsResponseTransformer(response.responseData),
            response.isSuccess,
            response.errorMessage
        )
    }

    override suspend fun addTransaction(addTransactionRequestDomainModel: AddTransactionRequestDomainModel): ResponseDomainModel<String?> {

        val response: ResponseDataModel<String?> =
            dataRepository.addTransaction(
                DomainToDataTransformers.addTransactionRequestTransformer(
                    addTransactionRequestDomainModel
                )
            )

        return ResponseDomainModel(
            response.responseData,
            response.isSuccess,
            response.errorMessage
        )
    }

    override suspend fun editTransaction(editTransactionRequestDomainModel: EditTransactionRequestDomainModel): ResponseDomainModel<String?> {

        val response: ResponseDataModel<String?> =
            dataRepository.editTransaction(
                DomainToDataTransformers.editTransactionRequestTransformer(
                    editTransactionRequestDomainModel
                )
            )

        return ResponseDomainModel(
            response.responseData,
            response.isSuccess,
            response.errorMessage
        )
    }

    override suspend fun deleteTransaction(transactionId: Int): ResponseDomainModel<String?> {
        val response: ResponseDataModel<String?> = dataRepository.deleteTransaction(transactionId)

        return ResponseDomainModel(
            response.responseData,
            response.isSuccess,
            response.errorMessage
        )
    }
}