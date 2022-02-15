package com.example.assetmanagement.usecases.transactionsActivity.selectionList.transformers

import com.example.assetmanagement.domain.model.SearchTypeDomain
import com.example.assetmanagement.usecases.common.model.AssetTypeModel
import com.example.assetmanagement.usecases.transactionsActivity.selectionList.model.SearchTypeModel

class SearchTypeTransformers {

    companion object {
        fun transformToSearchTypeModel(assetTypeModel: AssetTypeModel): SearchTypeModel {
            return when (assetTypeModel) {
                AssetTypeModel.CURRENCY -> SearchTypeModel.CURRENCY
                AssetTypeModel.STOCK -> SearchTypeModel.STOCK
                AssetTypeModel.CRYPTO -> SearchTypeModel.CRYPTO
            }
        }

        fun transformToSearchTypeDomain(searchTypeModel: SearchTypeModel): SearchTypeDomain {
            return when (searchTypeModel) {
                SearchTypeModel.CURRENCY -> SearchTypeDomain.CURRENCY
                SearchTypeModel.STOCK -> SearchTypeDomain.STOCK
                SearchTypeModel.CRYPTO -> SearchTypeDomain.CRYPTO
            }
        }

    }
}