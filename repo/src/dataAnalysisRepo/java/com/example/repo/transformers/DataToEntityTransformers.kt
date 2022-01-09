package com.example.repo.transformers

import com.example.repo.database.mechanism.transactions_table.TransactionDetailsEntity
import com.example.repo.model.AddTransactionRequestDataModel
import com.example.repo.model.EditTransactionRequestDataModel

class DataToEntityTransformers {

    companion object {
        fun transformToTransactionDetailsEntity(
            addTransactionRequestDataModel: AddTransactionRequestDataModel
        ) =
            TransactionDetailsEntity(
                addTransactionRequestDataModel.assetsName,
                addTransactionRequestDataModel.quantity,
                addTransactionRequestDataModel.price,
                addTransactionRequestDataModel.priceCurrency,
                addTransactionRequestDataModel.date,
                AssetTypeConverters.convertAssetType(addTransactionRequestDataModel.assetType),
                TransactionTypeConverters.convertTransactionType(addTransactionRequestDataModel.transactionType)
            )


        fun transformToTransactionDetailsEntity(
            editTransactionRequestDataModel: EditTransactionRequestDataModel
        ) =
            TransactionDetailsEntity(
                editTransactionRequestDataModel.transactionId,
                editTransactionRequestDataModel.assetsName,
                editTransactionRequestDataModel.quantity,
                editTransactionRequestDataModel.price,
                editTransactionRequestDataModel.priceCurrency,
                editTransactionRequestDataModel.date,
                AssetTypeConverters.convertAssetType(editTransactionRequestDataModel.assetType),
                TransactionTypeConverters.convertTransactionType(editTransactionRequestDataModel.transactionType)
            )

    }
}