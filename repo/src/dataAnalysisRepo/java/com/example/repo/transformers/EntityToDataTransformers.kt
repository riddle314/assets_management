package com.example.repo.transformers

import com.example.repo.model.TransactionDetailsResponseDataModel
import com.example.repo.model.TransactionItemResponseDataModel
import com.example.repo.database.mechanism.transactions_table.TransactionDetailsEntity

class EntityToDataTransformers {

    companion object {

        fun transform(transactionDetailsEntityList: List<TransactionDetailsEntity>): ArrayList<TransactionItemResponseDataModel> {
            var transactionItemDomainModelList = ArrayList<TransactionItemResponseDataModel>(0)
            for (transactionDetailsEntity in transactionDetailsEntityList) {
                transactionItemDomainModelList.add(
                    transformToTransactionItemDomainModel(
                        transactionDetailsEntity
                    )
                )
            }
            return transactionItemDomainModelList
        }

        fun transformToTransactionItemDomainModel(transactionDetailsEntity: TransactionDetailsEntity) =
            TransactionItemResponseDataModel(
                transactionDetailsEntity.transactionId,
                transactionDetailsEntity.assetsName,
                transactionDetailsEntity.quantity,
                transactionDetailsEntity.price,
                transactionDetailsEntity.priceCurrency,
                transactionDetailsEntity.date,
                AssetTypeConverters.getAssetType(transactionDetailsEntity.assetType),
                TransactionTypeConverters.getTransactionType(transactionDetailsEntity.transactionType)
            )

        fun transformToTransactionDetailsDomainModel(transactionDetailsEntity: TransactionDetailsEntity?): TransactionDetailsResponseDataModel? {
            return if (transactionDetailsEntity != null) {
                TransactionDetailsResponseDataModel(
                    transactionDetailsEntity.transactionId,
                    transactionDetailsEntity.assetsName,
                    transactionDetailsEntity.quantity,
                    transactionDetailsEntity.price,
                    transactionDetailsEntity.priceCurrency,
                    transactionDetailsEntity.date,
                    AssetTypeConverters.getAssetType(transactionDetailsEntity.assetType),
                    TransactionTypeConverters.getTransactionType(transactionDetailsEntity.transactionType)
                )
            } else {
                null
            }
        }
    }


}