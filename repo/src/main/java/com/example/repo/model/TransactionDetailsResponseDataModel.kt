package com.example.repo.model

/**
 * A data class for the domain type of transactions details
 */
 data class TransactionDetailsResponseDataModel(
    val transactionId: Int,
    val assetsName: String,
    val quantity: Double,
    val price: Double,
    val priceCurrency: String,
    val date: Long,
    val assetType: AssetTypeData,
    val transactionType: TransactionTypeData
)
