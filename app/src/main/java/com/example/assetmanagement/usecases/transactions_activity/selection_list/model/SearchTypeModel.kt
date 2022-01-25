package com.example.assetmanagement.usecases.transactions_activity.selection_list.model

import android.content.Context
import com.example.assetmanagement.R

enum class SearchTypeModel() {
    CURRENCY, STOCK, CRYPTO;

    // get the name of the type
    fun getName(context: Context): String = when (this) {
        SearchTypeModel.CURRENCY -> context.getString(R.string.currency)
        SearchTypeModel.STOCK -> context.getString(R.string.stock)
        SearchTypeModel.CRYPTO -> context.getString(R.string.crypto)
    }
}