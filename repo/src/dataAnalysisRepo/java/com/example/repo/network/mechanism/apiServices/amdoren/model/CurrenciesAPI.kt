package com.example.repo.network.mechanism.apiServices.amdoren.model

import com.google.gson.annotations.SerializedName

data class CurrenciesAPI(
    var error: Int,
    @SerializedName("error_message") var errorMessage: String,
    var currencies: List<CurrencyAPI> = arrayListOf()
)