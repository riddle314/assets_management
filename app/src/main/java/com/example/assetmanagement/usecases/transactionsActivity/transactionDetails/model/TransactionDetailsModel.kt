package com.example.assetmanagement.usecases.transactionsActivity.transactionDetails.model

import com.example.assetmanagement.common.model.AssetTypeModel
import com.example.assetmanagement.common.model.TransactionTypeModel

/**
 * A data class for the TransactionItemViewHolder
 */
data class TransactionDetailsModel(
    val transactionId: Int,
    val assetsName: String,
    val quantity: String,
    val price: String,
    val date: String,
    val assetType: AssetTypeModel,
    val transactionType: TransactionTypeModel
)
