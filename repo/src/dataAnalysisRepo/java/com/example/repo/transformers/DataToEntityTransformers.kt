package com.example.repo.transformers

import com.example.repo.database.mechanism.tables.CurrencyEntity
import com.example.repo.database.mechanism.tables.TransactionDetailsEntity
import com.example.repo.model.AddTransactionRequestDataModel
import com.example.repo.model.EditTransactionRequestDataModel
import com.example.repo.model.SelectionListResultDataModel

class DataToEntityTransformers {

    companion object {
        fun transformToTransactionDetailsEntity(
            source: AddTransactionRequestDataModel
        ) =
            TransactionDetailsEntity(
                source.assetsName,
                source.quantity,
                source.price,
                source.priceCurrency,
                source.date,
                AssetTypeConverters.convertAssetType(source.assetType),
                TransactionTypeConverters.convertTransactionType(source.transactionType)
            )


        fun transformToTransactionDetailsEntity(
            source: EditTransactionRequestDataModel
        ) =
            TransactionDetailsEntity(
                source.transactionId,
                source.assetsName,
                source.quantity,
                source.price,
                source.priceCurrency,
                source.date,
                AssetTypeConverters.convertAssetType(source.assetType),
                TransactionTypeConverters.convertTransactionType(source.transactionType)
            )

        fun transformCurrenciesToListOfCurrencyEntity(source: List<SelectionListResultDataModel>): List<CurrencyEntity> {
            val result = ArrayList<CurrencyEntity>(0)
            if (!source.isNullOrEmpty()) {
                for (item in source) {
                    result.add(
                        transformToCurrencyEntity(item)
                    )
                }
            }
            return result
        }

        private fun transformToCurrencyEntity(source: SelectionListResultDataModel) =
            CurrencyEntity(
                source.name,
                source.description
            )

    }
}