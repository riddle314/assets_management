package com.example.repo.network

import com.example.repo.model.ResponseDataModel
import com.example.repo.model.SelectionListResultDataModel

interface NetworkService {

    fun getAllCurrencies(): ResponseDataModel<List<SelectionListResultDataModel>>

}