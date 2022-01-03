package com.example.assetmanagement.data.data_analysis_repo.model

/**
 * A data class for the domain type of transaction item
 */
data class TransactionItemResponseDataModel(
    val transactionId: Int,
    val assetsName: String,
    val quantity: Double,
    val price: Double,
    val priceCurrency: String,
    val date: Long,
    val assetType: AssetTypeData,
    val transactionType: TransactionTypeData
)
