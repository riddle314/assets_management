package com.example.assetmanagement.domain.transformers

import com.example.assetmanagement.data.data_analysis_repo.model.AddTransactionRequestDataModel
import com.example.assetmanagement.data.data_analysis_repo.model.AssetTypeData
import com.example.assetmanagement.data.data_analysis_repo.model.EditTransactionRequestDataModel
import com.example.assetmanagement.data.data_analysis_repo.model.TransactionTypeData
import com.example.assetmanagement.domain.model.AddTransactionRequestDomainModel
import com.example.assetmanagement.domain.model.AssetTypeDomain
import com.example.assetmanagement.domain.model.EditTransactionRequestDomainModel
import com.example.assetmanagement.domain.model.TransactionTypeDomain

class DomainToDataTransformers {

    companion object {

        fun addTransactionRequestTransformer(source: AddTransactionRequestDomainModel): AddTransactionRequestDataModel {
            return AddTransactionRequestDataModel(
                source.assetsName,
                source.quantity,
                source.price,
                source.priceCurrency,
                source.date,
                assetTypeConverter(source.assetType),
                transactionTypeConverter(source.transactionType)
            )
        }

        fun editTransactionRequestTransformer(source: EditTransactionRequestDomainModel): EditTransactionRequestDataModel {
            return EditTransactionRequestDataModel(
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

        private fun assetTypeConverter(source: AssetTypeDomain): AssetTypeData {
            return when (source) {
                AssetTypeDomain.CURRENCY -> AssetTypeData.CURRENCY
                AssetTypeDomain.STOCK -> AssetTypeData.STOCK
                AssetTypeDomain.CRYPTO -> AssetTypeData.CRYPTO
            }
        }

        private fun transactionTypeConverter(source: TransactionTypeDomain): TransactionTypeData {
            return when (source) {
                TransactionTypeDomain.BUY -> TransactionTypeData.BUY
                TransactionTypeDomain.DIVIDEND -> TransactionTypeData.DIVIDEND
                TransactionTypeDomain.SELL -> TransactionTypeData.SELL
            }
        }
    }
}