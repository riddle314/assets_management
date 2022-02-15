package com.example.repo.transformers

import com.example.repo.model.SelectionListResultDataModel
import com.example.repo.network.mechanism.apiServices.amdoren.model.CurrencyAPI

class ApiToDataTransformers {

    companion object {
        fun transformList(source: List<CurrencyAPI>): List<SelectionListResultDataModel> {
            val result: ArrayList<SelectionListResultDataModel> = arrayListOf()
            if (!source.isNullOrEmpty()) {
                for (item in source) {
                    result.add(SelectionListResultDataModel(item.currency, item.description))
                }
            }
            return result
        }

        fun transformMap(source: Map<String, String>): List<SelectionListResultDataModel> {
            val result: ArrayList<SelectionListResultDataModel> = arrayListOf()
            if (!source.isNullOrEmpty()) {
                for (item in source) {
                    result.add(SelectionListResultDataModel(item.key, item.value))
                }
            }
            return result
        }
    }
}