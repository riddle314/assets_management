package com.example.assetmanagement.data.data_analysis_repo.model

data class EditTransactionRequestDataModel(
    val transactionId: Int,
    val assetsName: String,
    val quantity: Double,
    val price: Double,
    val priceCurrency: String,
    val date: Long,
    val assetType: AssetTypeData,
    val transactionType: TransactionTypeData
)