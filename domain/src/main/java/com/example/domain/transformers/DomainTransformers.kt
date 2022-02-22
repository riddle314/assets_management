package com.example.domain.transformers

import com.example.domain.model.SearchTypeDomain
import com.example.domain.model.SelectionListResponseDomainModel
import com.example.domain.model.SelectionListResultDomainModel

class DomainTransformers {

    companion object {

        fun selectionListResultListTransformer(
            searchType: SearchTypeDomain,
            source: List<SelectionListResponseDomainModel>
        ): List<SelectionListResultDomainModel> {
            val output: ArrayList<SelectionListResultDomainModel> = arrayListOf()
            if (!source.isNullOrEmpty()) {
                for (item in source) {
                    output.add(selectionListResultTransformer(searchType, item))
                }
            }
            return output
        }

        private fun selectionListResultTransformer(
            searchType: SearchTypeDomain,
            source: SelectionListResponseDomainModel
        ): SelectionListResultDomainModel {
            return SelectionListResultDomainModel(
                source.name,
                source.description,
                searchType
            )
        }
    }

}