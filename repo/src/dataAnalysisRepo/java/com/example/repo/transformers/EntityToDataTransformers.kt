package com.example.repo.transformers

import com.example.repo.database.mechanism.tables.CurrencyEntity
import com.example.repo.database.mechanism.tables.TransactionDetailsEntity
import com.example.repo.model.SelectionListResultDataModel
import com.example.repo.model.TransactionDetailsResponseDataModel
import com.example.repo.model.TransactionItemResponseDataModel

class EntityToDataTransformers {

    companion object {

        fun transform(source: List<TransactionDetailsEntity>): ArrayList<TransactionItemResponseDataModel> {
            val result = ArrayList<TransactionItemResponseDataModel>(0)
            if (!source.isNullOrEmpty()) {
                for (item in source) {
                    result.add(
                        transformToTransactionItemDomainModel(
                            item
                        )
                    )
                }
            }
            return result
        }

        private fun transformToTransactionItemDomainModel(source: TransactionDetailsEntity) =
            TransactionItemResponseDataModel(
                source.transactionId,
                source.assetsName,
                source.quantity,
                source.price,
                source.priceCurrency,
                source.date,
                AssetTypeConverters.getAssetType(source.assetType),
                TransactionTypeConverters.getTransactionType(source.transactionType)
            )

        fun transformToTransactionDetailsDomainModel(source: TransactionDetailsEntity?): TransactionDetailsResponseDataModel? {
            return if (source != null) {
                TransactionDetailsResponseDataModel(
                    source.transactionId,
                    source.assetsName,
                    source.quantity,
                    source.price,
                    source.priceCurrency,
                    source.date,
                    AssetTypeConverters.getAssetType(source.assetType),
                    TransactionTypeConverters.getTransactionType(source.transactionType)
                )
            } else {
                null
            }
        }


        fun transformCurrenciesToListOfSelectionListResultDataModel(source: List<CurrencyEntity>): ArrayList<SelectionListResultDataModel> {
            val result = ArrayList<SelectionListResultDataModel>(0)
            if (!source.isNullOrEmpty()) {
                for (item in source) {
                    result.add(
                        transformCurrencyToSelectionListResultDataModel(item)
                    )
                }
            }
            return result
        }

        private fun transformCurrencyToSelectionListResultDataModel(source: CurrencyEntity) =
            SelectionListResultDataModel(
                source.currency,
                source.description
            )
    }


}