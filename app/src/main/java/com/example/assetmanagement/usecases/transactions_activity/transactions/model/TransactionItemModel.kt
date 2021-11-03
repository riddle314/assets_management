package com.example.assetmanagement.usecases.transactions_activity.transactions.model

import com.example.assetmanagement.usecases.common.model.AssetTypeModel
import com.example.assetmanagement.usecases.common.model.TransactionTypeModel

/**
 * A data class for the TransactionItemViewHolder
 */
data class TransactionItemModel(
    val transactionId: Int,
    val assetsName: String,
    val quantity: String,
    val price: String,
    val date: String,
    val assetType: AssetTypeModel,
    val transactionType: TransactionTypeModel
)
