package com.example.repo.transformers

import com.example.repo.model.SelectionListResultDataModel
import com.example.repo.network.mechanism.model.CurrencyAPI

class ApiToDataTransformers {

    companion object {
        fun transform(source: List<CurrencyAPI>): List<SelectionListResultDataModel> {
            val result: ArrayList<SelectionListResultDataModel> = arrayListOf()
            if (!source.isNullOrEmpty()) {
                for (item in source) {
                    result.add(SelectionListResultDataModel(item.currency, item.description))
                }
            }
            return result
        }
    }
}