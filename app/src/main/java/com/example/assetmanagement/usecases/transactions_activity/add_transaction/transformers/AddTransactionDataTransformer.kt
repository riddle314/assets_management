package com.example.assetmanagement.usecases.transactions_activity.add_transaction.transformers

import com.example.assetmanagement.domain.model.AddTransactionRequestDomainModel
import com.example.assetmanagement.domain.model.EditTransactionRequestDomainModel
import com.example.assetmanagement.domain.model.TransactionDetailsResponseDomainModel
import com.example.assetmanagement.usecases.common.model.AssetTypeModel
import com.example.assetmanagement.usecases.common.transformers.DataTransformers
import com.example.assetmanagement.usecases.transactions_activity.add_transaction.model.AddTransactionModel
import com.example.assetmanagement.usecases.transactions_activity.add_transaction.model.AssetModel

class AddTransactionDataTransformer {

    companion object {

        fun transformToResponse(source: TransactionDetailsResponseDomainModel): AddTransactionModel {
            val assetType: AssetTypeModel = DataTransformers.transformAssetType(source.assetType)
            return AddTransactionModel(
                source.transactionId,
                AssetModel(source.assetsName, assetType),
                source.quantity.toString(),
                source.price.toString(),
                source.priceCurrency,
                source.date,
                assetType,
                DataTransformers.transformTransactionType(source.transactionType)
            )
        }


        fun transformToEditRequest(addTransactionModel: AddTransactionModel) =
            EditTransactionRequestDomainModel(
                addTransactionModel.transactionId,
                addTransactionModel.assetModel.assetName,
                addTransactionModel.quantity.toDouble(),
                addTransactionModel.price.toDouble(),
                addTransactionModel.priceCurrency,
                addTransactionModel.date,
                DataTransformers.transformAssetType(addTransactionModel.assetType),
                DataTransformers.transformTransactionType(addTransactionModel.transactionType)
            )

        fun transformToAddRequest(addTransactionModel: AddTransactionModel) =
            AddTransactionRequestDomainModel(
                addTransactionModel.assetModel.assetName,
                addTransactionModel.quantity.toDouble(),
                addTransactionModel.price.toDouble(),
                addTransactionModel.priceCurrency,
                addTransactionModel.date,
                DataTransformers.transformAssetType(addTransactionModel.assetType),
                DataTransformers.transformTransactionType(addTransactionModel.transactionType)
            )


    }

}