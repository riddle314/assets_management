package com.example.domain

import com.example.domain.model.*
import com.example.domain.transformers.DomainTransformers
import javax.inject.Inject

class DomainRepositoryImpl @Inject constructor(
    private val dataRepository: DataRepository
) : DomainRepository {

    override suspend fun getAllTransactions(): ResponseDomainModel<List<TransactionItemResponseDomainModel>> {

        val response: ResponseDomainModel<List<TransactionItemResponseDomainModel>> =
            dataRepository.getAllTransactions()

        return ResponseDomainModel(
            response.responseData,
            response.isSuccess,
            response.errorMessage
        )
    }

    override suspend fun getTransactionsForQuery(query: String): ResponseDomainModel<List<TransactionItemResponseDomainModel>> {

        val response: ResponseDomainModel<List<TransactionItemResponseDomainModel>> =
            dataRepository.getTransactionsForQuery(query)

        return ResponseDomainModel(
            response.responseData,
            response.isSuccess,
            response.errorMessage
        )
    }

    override suspend fun getTransactionDetails(transactionId: Int): ResponseDomainModel<TransactionDetailsResponseDomainModel?> {

        val response: ResponseDomainModel<TransactionDetailsResponseDomainModel?> =
            dataRepository.getTransactionDetails(transactionId)

        return ResponseDomainModel(response.responseData, response.isSuccess, response.errorMessage)
    }

    override suspend fun addTransaction(addTransactionRequestDomainModel: AddTransactionRequestDomainModel): ResponseDomainModel<String?> {

        val response: ResponseDomainModel<String?> =
            dataRepository.addTransaction(addTransactionRequestDomainModel)

        return ResponseDomainModel(
            response.responseData,
            response.isSuccess,
            response.errorMessage
        )
    }

    override suspend fun editTransaction(editTransactionRequestDomainModel: EditTransactionRequestDomainModel): ResponseDomainModel<String?> {

        val response: ResponseDomainModel<String?> =
            dataRepository.editTransaction(editTransactionRequestDomainModel)

        return ResponseDomainModel(
            response.responseData,
            response.isSuccess,
            response.errorMessage
        )
    }

    override suspend fun deleteTransaction(transactionId: Int): ResponseDomainModel<String?> {
        val response: ResponseDomainModel<String?> =
            dataRepository.deleteTransaction(transactionId)

        return ResponseDomainModel(
            response.responseData,
            response.isSuccess,
            response.errorMessage
        )
    }

    override suspend fun getAllSearchItems(searchTypeDomain: SearchTypeDomain): ResponseDomainModel<List<SelectionListResultDomainModel>> {
        val response: ResponseDomainModel<List<SelectionListResponseDomainModel>> =
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
            DomainTransformers.selectionListResultListTransformer(
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
        val response: ResponseDomainModel<List<SelectionListResponseDomainModel>> =
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
            DomainTransformers.selectionListResultListTransformer(
                searchTypeDomain,
                response.responseData
            ),
            response.isSuccess,
            response.errorMessage
        )
    }
}