package com.example.assetmanagement.domain.model

/**
 * A data class for the domain type of transactions details
 */
 data class TransactionDetailsResponseDomainModel(
    val transactionId: Int,
    val assetsName: String,
    val quantity: String,
    val price: Double,
    val priceCurrency: String,
    val date: String,
    val assetType: AssetTypeDomain,
    val transactionType: TransactionTypeDomain
)
