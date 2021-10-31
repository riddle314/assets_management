package com.example.assetmanagement.data.data_analysis_repo.transformers

import com.example.assetmanagement.data.data_analysis_repo.database.mechanism.transactions_table.TransactionDetailsEntity
import com.example.assetmanagement.domain.model.AddTransactionRequestDomainModel
import com.example.assetmanagement.domain.model.EditTransactionRequestDomainModel

class DataTransformersToEntity {

    companion object {
        fun transformToTransactionDetailsEntity(
            addTransactionRequestDomainModel: AddTransactionRequestDomainModel
        ) =
            TransactionDetailsEntity(
                addTransactionRequestDomainModel.assetsName,
                addTransactionRequestDomainModel.quantity,
                addTransactionRequestDomainModel.price,
                addTransactionRequestDomainModel.priceCurrency,
                addTransactionRequestDomainModel.date,
                AssetTypeConverters.convertAssetType(addTransactionRequestDomainModel.assetType),
                addTransactionRequestDomainModel.transactionType
            )


        fun transformToTransactionDetailsEntity(
            editTransactionRequestDomainModel: EditTransactionRequestDomainModel
        ) =
            TransactionDetailsEntity(
                editTransactionRequestDomainModel.transactionId,
                editTransactionRequestDomainModel.assetsName,
                editTransactionRequestDomainModel.quantity,
                editTransactionRequestDomainModel.price,
                editTransactionRequestDomainModel.priceCurrency,
                editTransactionRequestDomainModel.date,
                AssetTypeConverters.convertAssetType(editTransactionRequestDomainModel.assetType),
                editTransactionRequestDomainModel.transactionType
            )


    }
}