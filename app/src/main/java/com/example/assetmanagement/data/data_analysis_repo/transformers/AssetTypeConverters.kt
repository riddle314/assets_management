package com.example.assetmanagement.data.data_analysis_repo.transformers

import com.example.assetmanagement.domain.model.AssetType

class AssetTypeConverters {

    companion object {
        fun getAssetType(assetType: Int): AssetType {
            return when (assetType) {
                1 -> AssetType.CURRENCY
                2 -> AssetType.STOCK
                3 -> AssetType.CRYPTO
                else -> AssetType.NONE
            }
        }

        fun convertAssetType(assetType: AssetType): Int {
            return when (assetType) {
                AssetType.CURRENCY -> 1
                AssetType.STOCK -> 2
                AssetType.CRYPTO -> 3
                else -> 0
            }
        }

    }
}