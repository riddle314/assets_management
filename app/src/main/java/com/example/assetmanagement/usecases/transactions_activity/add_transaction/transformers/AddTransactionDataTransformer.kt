package com.example.assetmanagement.usecases.transactions_activity.add_transaction.transformers

import com.example.assetmanagement.domain.model.TransactionDetailsResponseDomainModel
import com.example.assetmanagement.domain.model.AddTransactionRequestDomainModel
import com.example.assetmanagement.domain.model.AssetType
import com.example.assetmanagement.domain.model.EditTransactionRequestDomainModel
import com.example.assetmanagement.usecases.transactions_activity.add_transaction.model.AddTransactionModel

class AddTransactionDataTransformer {

    companion object {

        fun transformToResponse(transactionDetailsResponseDomainModel: TransactionDetailsResponseDomainModel) =
            AddTransactionModel(
                transactionDetailsResponseDomainModel.transactionId,
                transactionDetailsResponseDomainModel.assetsName,
                transactionDetailsResponseDomainModel.quantity,
                transactionDetailsResponseDomainModel.price.toString(),
                transactionDetailsResponseDomainModel.priceCurrency,
                transactionDetailsResponseDomainModel.date,
                transactionDetailsResponseDomainModel.assetType.getTypeName(),
                transactionDetailsResponseDomainModel.transactionType
            )

        fun transformToEditRequest(addTransactionModel: AddTransactionModel) =
            EditTransactionRequestDomainModel(
                addTransactionModel.transactionId,
                addTransactionModel.assetsName,
                addTransactionModel.quantity,
                addTransactionModel.price.toDouble(),
                addTransactionModel.priceCurrency,
                addTransactionModel.date,
                getAssetType(addTransactionModel.assetType),
                addTransactionModel.transactionType
            )

        fun transformToAddRequest(addTransactionModel: AddTransactionModel) =
            AddTransactionRequestDomainModel(
                addTransactionModel.assetsName,
                addTransactionModel.quantity,
                addTransactionModel.price.toDouble(),
                addTransactionModel.priceCurrency,
                addTransactionModel.date,
                getAssetType(addTransactionModel.assetType),
                addTransactionModel.transactionType
            )


        private fun getAssetType(assetType: String): AssetType {
            for (assetTypeEnum in AssetType.values()) {
                if (assetTypeEnum.getTypeName() == assetType) {
                    return assetTypeEnum
                }
            }
            return AssetType.NONE
        }

    }

}