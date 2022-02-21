package com.example.assetmanagement.domain

import com.example.assetmanagement.domain.model.*
import com.example.assetmanagement.domain.transformers.DataToDomainTransformers
import com.example.assetmanagement.domain.transformers.DomainToDataTransformers
import com.example.repo.DataRepository
import com.example.repo.model.ResponseDataModel
import com.example.repo.model.SelectionListResultDataModel
import com.example.repo.model.TransactionDetailsResponseDataModel
import com.example.repo.model.TransactionItemResponseDataModel
import javax.inject.Inject

class DomainRepositoryImpl @Inject constructor(
    private val dataRepository: DataRepository
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
        val response: ResponseDataModel<String?> =
            dataRepository.deleteTransaction(transactionId)

        return ResponseDomainModel(
            response.responseData,
            response.isSuccess,
            response.errorMessage
        )
    }

    override suspend fun getAllSearchItems(searchTypeDomain: SearchTypeDomain): ResponseDomainModel<List<SelectionListResultDomainModel>> {
        val response: ResponseDataModel<List<SelectionListResultDataModel>> =
            when (searchTypeDomain) {
                SearchTypeDomain.CRYPTO -> {
                    dataRepository.getAllCrypto()
                }
                SearchTypeDomain.STOCK -> {
                    dataRepository.getAllStocks()
                }
                SearchTypeDomain.CURRENCY -> {
                    dataRepository.getAllCurrencies()
                }
            }

        return ResponseDomainModel(
            DataToDomainTransformers.selectionListResultListTransformer(
                searchTypeDomain,
                response.responseData
            ),
            response.isSuccess,
            response.errorMessage
        )
    }

    override suspend fun getSearchItemsForQuery(
        query: String,
        searchTypeDomain: SearchTypeDomain
    ): ResponseDomainModel<List<SelectionListResultDomainModel>> {
        val response: ResponseDataModel<List<SelectionListResultDataModel>> =
            when (searchTypeDomain) {
                SearchTypeDomain.CRYPTO -> {
                    dataRepository.getCryptoForQuery(query)
                }
                SearchTypeDomain.STOCK -> {
                    dataRepository.getStocksForQuery(query)
                }
                SearchTypeDomain.CURRENCY -> {
                    dataRepository.getCurrenciesForQuery(query)
                }
            }

        return ResponseDomainModel(
            DataToDomainTransformers.selectionListResultListTransformer(
                searchTypeDomain,
                response.responseData
            ),
            response.isSuccess,
            response.errorMessage
        )
    }
}