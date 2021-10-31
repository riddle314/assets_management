package com.example.assetmanagement.usecases.transactions_activity.transaction_details.model

/**
 * A data class for the TransactionItemViewHolder
 */
data class TransactionDetailsModel(
    val transactionId: Int,
    val assetsName: String,
    val quantity: String,
    val price: String,
    val date: String,
    val assetType: String,
    val transactionType: String
)
