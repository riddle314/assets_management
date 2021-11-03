package com.example.assetmanagement.domain.model

data class EditTransactionRequestDomainModel(
    val transactionId: Int,
    val assetsName: String,
    val quantity: String,
    val price: Double,
    val priceCurrency: String,
    val date: String,
    val assetType: AssetTypeDomain,
    val transactionType: TransactionTypeDomain
)