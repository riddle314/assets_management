package com.example.repo.network.mechanism.apiServices.amdoren

import com.example.repo.network.mechanism.apiServices.amdoren.model.CurrenciesAPI
import retrofit2.Call
import retrofit2.http.GET


interface AmdorenService {

    @GET("api/currency_list.php")
    fun getAllCurrencies(): Call<CurrenciesAPI>
}