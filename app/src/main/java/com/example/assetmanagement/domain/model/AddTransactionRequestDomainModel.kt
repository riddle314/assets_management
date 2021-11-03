package com.example.assetmanagement.domain.model

data class AddTransactionRequestDomainModel(
    val assetsName: String,
    val quantity: String,
    val price: Double,
    val priceCurrency: String,
    val date: String,
    val assetType: AssetTypeDomain,
    val transactionType: TransactionTypeDomain
)