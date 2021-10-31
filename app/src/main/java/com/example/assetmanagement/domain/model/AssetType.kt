package com.example.assetmanagement.domain.model

enum class AssetType {
    CURRENCY, STOCK, CRYPTO, NONE;

    // get the name of the type
    fun getTypeName(): String = when (this) {
        CURRENCY -> CURRENCY.name
        STOCK -> STOCK.name
        CRYPTO -> CRYPTO.name
        NONE -> NONE.name
    }

}
