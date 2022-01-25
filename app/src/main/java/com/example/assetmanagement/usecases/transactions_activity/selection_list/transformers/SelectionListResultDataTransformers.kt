package com.example.assetmanagement.usecases.transactions_activity.selection_list.transformers

import com.example.assetmanagement.domain.model.SearchTypeDomain
import com.example.assetmanagement.domain.model.SelectionListResultDomainModel
import com.example.assetmanagement.usecases.transactions_activity.selection_list.model.SearchTypeModel
import com.example.assetmanagement.usecases.transactions_activity.selection_list.model.SelectionListResultModel

class SelectionListResultDataTransformers {

    companion object {

        fun transform(source: List<SelectionListResultDomainModel>): ArrayList<SelectionListResultModel> {
            val target = ArrayList<SelectionListResultModel>(0)
            if (!source.isNullOrEmpty()) {
                for (item in source) {
                    target.add(transform(item))
                }
            }
            return target
        }

        private fun transform(source: SelectionListResultDomainModel): SelectionListResultModel {
            val searchTypeModel = when (source.searchType) {
                SearchTypeDomain.CURRENCY -> SearchTypeModel.CURRENCY
                SearchTypeDomain.STOCK -> SearchTypeModel.STOCK
                SearchTypeDomain.CRYPTO -> SearchTypeModel.CRYPTO
            }
            return SelectionListResultModel(
                source.name,
                source.description,
                searchTypeModel
            )
        }

    }
}