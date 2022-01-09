package com.example.repo.transformers

import com.example.repo.model.AssetTypeData

class AssetTypeConverters {

    companion object {
        private const val CURRENCY = 1
        private const val STOCK = 2
        private const val CRYPTO = 3

        fun getAssetType(assetType: Int): AssetTypeData {
            return when (assetType) {
                CURRENCY -> AssetTypeData.CURRENCY
                STOCK -> AssetTypeData.STOCK
                else -> AssetTypeData.CRYPTO
            }
        }


        fun convertAssetType(assetType: AssetTypeData): Int {
            return when (assetType) {
                AssetTypeData.CURRENCY -> CURRENCY
                AssetTypeData.STOCK -> STOCK
                AssetTypeData.CRYPTO -> CRYPTO
            }
        }

    }
}