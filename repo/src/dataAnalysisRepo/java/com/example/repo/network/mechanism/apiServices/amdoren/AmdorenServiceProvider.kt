package com.example.repo.network.mechanism.apiServices.amdoren

import android.content.Context
import com.example.repo.R
import com.example.repo.network.mechanism.RetrofitFactory
import okhttp3.Interceptor

object AmdorenServiceProvider {

    private var service: AmdorenService? = null

    fun provideService(context: Context): AmdorenService {
        if (service == null) {
            // create interceptor for authentication query to OkHttpClient
            val addApiKeyQueryInterceptor: Interceptor = RetrofitFactory.getQueryInterceptor(
                context.resources.getString(R.string.api_Key),
                context.resources.getString(R.string.amdoren_api_key)
            )

            service =
                RetrofitFactory.getRetrofitService<AmdorenService>(
                    addApiKeyQueryInterceptor, context.getString(R.string.amdoren_base_url)
                )
        }
        return service as AmdorenService
    }
}