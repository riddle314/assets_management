package com.example.assetmanagement.domain.model

data class AddTransactionRequestDomainModel(
    val assetsName: String,
    val quantity: Double,
    val price: Double,
    val priceCurrency: String,
    val date: Long,
    val assetType: AssetTypeDomain,
    val transactionType: TransactionTypeDomain
)