package com.example.assetmanagement.usecases.transactions_activity.transactions.model

/**
 * A data class for the TransactionItemViewHolder
 */
data class TransactionItemModel(
    val transactionId: Int,
    val assetsName: String,
    val quantity: String,
    val price: String,
    val date: String,
    val assetType: String,
    val transactionType: String
)
