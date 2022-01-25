package com.example.repo.network.mechanism

import com.example.repo.network.mechanism.model.CurrenciesAPI
import retrofit2.Call
import retrofit2.http.GET


interface AmdorenApiEndpointService {

    @GET("api/currency_list.php")
    fun getCurrencies(): Call<CurrenciesAPI>
}