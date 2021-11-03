package com.example.assetmanagement.usecases.transactions_activity.add_transaction.transformers

import com.example.assetmanagement.domain.model.AddTransactionRequestDomainModel
import com.example.assetmanagement.domain.model.EditTransactionRequestDomainModel
import com.example.assetmanagement.domain.model.TransactionDetailsResponseDomainModel
import com.example.assetmanagement.usecases.common.transformers.DataTransformers
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
                DataTransformers.transformAssetType(transactionDetailsResponseDomainModel.assetType),
                DataTransformers.transformTransactionType(transactionDetailsResponseDomainModel.transactionType)
            )

        fun transformToEditRequest(addTransactionModel: AddTransactionModel) =
            EditTransactionRequestDomainModel(
                addTransactionModel.transactionId,
                addTransactionModel.assetsName,
                addTransactionModel.quantity,
                addTransactionModel.price.toDouble(),
                addTransactionModel.priceCurrency,
                addTransactionModel.date,
                DataTransformers.transformAssetType(addTransactionModel.assetType),
                DataTransformers.transformTransactionType(addTransactionModel.transactionType)
            )

        fun transformToAddRequest(addTransactionModel: AddTransactionModel) =
            AddTransactionRequestDomainModel(
                addTransactionModel.assetsName,
                addTransactionModel.quantity,
                addTransactionModel.price.toDouble(),
                addTransactionModel.priceCurrency,
                addTransactionModel.date,
                DataTransformers.transformAssetType(addTransactionModel.assetType),
                DataTransformers.transformTransactionType(addTransactionModel.transactionType)
            )


    }

}