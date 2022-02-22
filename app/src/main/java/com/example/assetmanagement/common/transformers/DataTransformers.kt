package com.example.assetmanagement.common.transformers

import com.example.domain.model.AssetTypeDomain
import com.example.domain.model.TransactionTypeDomain
import com.example.assetmanagement.common.model.AssetTypeModel
import com.example.assetmanagement.common.model.TransactionTypeModel

class DataTransformers {

    companion object {
        fun transformAssetType(assetTypeModel: AssetTypeModel): AssetTypeDomain {
            return when (assetTypeModel) {
                AssetTypeModel.CURRENCY -> AssetTypeDomain.CURRENCY
                AssetTypeModel.STOCK -> AssetTypeDomain.STOCK
                AssetTypeModel.CRYPTO -> AssetTypeDomain.CRYPTO
            }
        }

        fun transformAssetType(assetTypeDomain: AssetTypeDomain): AssetTypeModel {
            return when (assetTypeDomain) {
                AssetTypeDomain.CURRENCY -> AssetTypeModel.CURRENCY
                AssetTypeDomain.STOCK -> AssetTypeModel.STOCK
                AssetTypeDomain.CRYPTO -> AssetTypeModel.CRYPTO
            }
        }

        fun transformTransactionType(transactionTypeModel: TransactionTypeModel): TransactionTypeDomain {
            return when (transactionTypeModel) {
                TransactionTypeModel.BUY -> TransactionTypeDomain.BUY
                TransactionTypeModel.SELL -> TransactionTypeDomain.SELL
                TransactionTypeModel.DIVIDEND -> TransactionTypeDomain.DIVIDEND
            }
        }

        fun transformTransactionType(transactionTypeDomain: TransactionTypeDomain): TransactionTypeModel {
            return when (transactionTypeDomain) {
                TransactionTypeDomain.BUY -> TransactionTypeModel.BUY
                TransactionTypeDomain.SELL -> TransactionTypeModel.SELL
                TransactionTypeDomain.DIVIDEND -> TransactionTypeModel.DIVIDEND
            }
        }
    }
}