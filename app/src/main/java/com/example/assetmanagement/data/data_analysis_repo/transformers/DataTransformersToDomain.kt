package com.example.assetmanagement.data.data_analysis_repo.transformers

import com.example.assetmanagement.domain.model.TransactionDetailsResponseDomainModel
import com.example.assetmanagement.domain.model.TransactionItemResponseDomainModel
import com.example.assetmanagement.data.data_analysis_repo.database.mechanism.transactions_table.TransactionDetailsEntity

class DataTransformersToDomain {

    companion object {

        fun transform(transactionDetailsEntityList: List<TransactionDetailsEntity>): ArrayList<TransactionItemResponseDomainModel> {
            var transactionItemDomainModelList = ArrayList<TransactionItemResponseDomainModel>(0)
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
            TransactionItemResponseDomainModel(
                transactionDetailsEntity.transactionId,
                transactionDetailsEntity.assetsName,
                transactionDetailsEntity.quantity,
                transactionDetailsEntity.price,
                transactionDetailsEntity.priceCurrency,
                transactionDetailsEntity.date,
                AssetTypeConverters.getAssetType(transactionDetailsEntity.assetType),
                TransactionTypeConverters.getTransactionType(transactionDetailsEntity.transactionType)
            )

        fun transformToTransactionDetailsDomainModel(transactionDetailsEntity: TransactionDetailsEntity?): TransactionDetailsResponseDomainModel? {
            return if (transactionDetailsEntity != null) {
                TransactionDetailsResponseDomainModel(
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