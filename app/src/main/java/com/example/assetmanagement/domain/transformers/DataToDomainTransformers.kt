package com.example.assetmanagement.domain.transformers

import com.example.assetmanagement.data.data_analysis_repo.model.AssetTypeData
import com.example.assetmanagement.data.data_analysis_repo.model.TransactionDetailsResponseDataModel
import com.example.assetmanagement.data.data_analysis_repo.model.TransactionItemResponseDataModel
import com.example.assetmanagement.data.data_analysis_repo.model.TransactionTypeData
import com.example.assetmanagement.domain.model.AssetTypeDomain
import com.example.assetmanagement.domain.model.TransactionDetailsResponseDomainModel
import com.example.assetmanagement.domain.model.TransactionItemResponseDomainModel
import com.example.assetmanagement.domain.model.TransactionTypeDomain

class DataToDomainTransformers {

    companion object {

        fun transactionItemResponseListTransformer(source: List<TransactionItemResponseDataModel>)
                : List<TransactionItemResponseDomainModel> {
            val output: ArrayList<TransactionItemResponseDomainModel> = arrayListOf()
            if (!source.isNullOrEmpty()) {
                for (listItem in source) {
                    output.add(transactionItemResponseTransformer(listItem))
                }
            }
            return output
        }

        fun transactionItemResponseTransformer(source: TransactionItemResponseDataModel): TransactionItemResponseDomainModel {
            return TransactionItemResponseDomainModel(
                source.transactionId,
                source.assetsName,
                source.quantity,
                source.price,
                source.priceCurrency,
                source.date,
                assetTypeConverter(source.assetType),
                transactionTypeConverter(source.transactionType)
            )
        }

        fun transactionDetailsResponseTransformer(source: TransactionDetailsResponseDataModel?): TransactionDetailsResponseDomainModel? {
            return if (source != null) {
                TransactionDetailsResponseDomainModel(
                    source.transactionId,
                    source.assetsName,
                    source.quantity,
                    source.price,
                    source.priceCurrency,
                    source.date,
                    assetTypeConverter(source.assetType),
                    transactionTypeConverter(source.transactionType)
                )
            } else {
                null
            }
        }

        private fun assetTypeConverter(source: AssetTypeData): AssetTypeDomain {
            return when (source) {
                AssetTypeData.CURRENCY -> AssetTypeDomain.CURRENCY
                AssetTypeData.STOCK -> AssetTypeDomain.STOCK
                AssetTypeData.CRYPTO -> AssetTypeDomain.CRYPTO
            }
        }

        private fun transactionTypeConverter(source: TransactionTypeData): TransactionTypeDomain {
            return when (source) {
                TransactionTypeData.BUY -> TransactionTypeDomain.BUY
                TransactionTypeData.DIVIDEND -> TransactionTypeDomain.DIVIDEND
                TransactionTypeData.SELL -> TransactionTypeDomain.SELL
            }
        }
    }
}