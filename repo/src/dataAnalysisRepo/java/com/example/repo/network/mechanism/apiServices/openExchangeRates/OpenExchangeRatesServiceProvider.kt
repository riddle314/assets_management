package com.example.repo.network.mechanism.apiServices.openExchangeRates

import android.content.Context
import com.example.repo.R
import com.example.repo.network.mechanism.RetrofitFactory

object OpenExchangeRatesServiceProvider {

    private var service: OpenExchangeRatesService? = null

    fun provideService(context: Context): OpenExchangeRatesService {
        if (service == null) {
            service = RetrofitFactory.getRetrofitService<OpenExchangeRatesService>(
                null, context.getString(R.string.open_exchange_rates_base_url)
            )
        }
        return service as OpenExchangeRatesService
    }
}