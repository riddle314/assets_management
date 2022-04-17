package com.example.assetmanagement.usecases.transactionsActivity.selectionList.model

import android.content.Context
import com.example.assetmanagement.R

enum class SearchTypeModel() {
    CURRENCY, STOCK, CRYPTO;

    // get the name of the type
    fun getName(context: Context): String = when (this) {
        CURRENCY -> context.getString(R.string.currency)
        STOCK -> context.getString(R.string.stock)
        CRYPTO -> context.getString(R.string.crypto)
    }
}