package com.example.assetmanagement.data.data_analysis_repo.transformers

import com.example.assetmanagement.domain.model.AssetTypeDomain

class AssetTypeConverters {

    companion object {
        private const val CURRENCY = 1
        private const val STOCK = 2
        private const val CRYPTO = 3

        fun getAssetType(assetType: Int): AssetTypeDomain {
            return when (assetType) {
                CURRENCY -> AssetTypeDomain.CURRENCY
                STOCK -> AssetTypeDomain.STOCK
                else -> AssetTypeDomain.CRYPTO
            }
        }


        fun convertAssetType(assetType: AssetTypeDomain): Int {
            return when (assetType) {
                AssetTypeDomain.CURRENCY -> CURRENCY
                AssetTypeDomain.STOCK -> STOCK
                AssetTypeDomain.CRYPTO -> CRYPTO
            }
        }

    }
}