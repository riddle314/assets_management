package com.example.repo.network.mechanism.apiServices.openExchangeRates


import retrofit2.Call
import retrofit2.http.GET


interface OpenExchangeRatesService {

    @GET("api/currencies.json")
    fun getAllCurrencies(): Call<Map<String,String>>
}