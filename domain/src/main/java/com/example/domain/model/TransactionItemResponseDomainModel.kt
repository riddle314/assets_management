package com.example.domain.model

/**
 * A data class for the domain type of transaction item
 */
data class TransactionItemResponseDomainModel(
    val transactionId: Int,
    val assetsName: String,
    val quantity: Double,
    val price: Double,
    val priceCurrency: String,
    val date: Long,
    val assetType: AssetTypeDomain,
    val transactionType: TransactionTypeDomain
)
