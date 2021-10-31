package com.example.assetmanagement.domain.model

/**
 * A data class for the domain type of transaction item
 */
data class TransactionItemResponseDomainModel(
    val transactionId: Int,
    val assetsName: String,
    val quantity: String,
    val price: Double,
    val priceCurrency: String,
    val date: String,
    val assetType: AssetType,
    val transactionType: String
)
