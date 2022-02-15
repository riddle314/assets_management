package com.example.repo.network.mechanism;

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun getRetrofit(
        queryInterceptor: Interceptor?,
        baseUrl: String
    ): Retrofit {

        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

        // add query interceptor
        if (queryInterceptor != null) {
            okHttpClientBuilder.addInterceptor(queryInterceptor)
        }

        // create interceptor for logging
        val httpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        okHttpClientBuilder.addNetworkInterceptor(httpLoggingInterceptor)

        val okHttpClient: OkHttpClient = okHttpClientBuilder.build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getQueryInterceptor(queryKey: String, queryValue: String): Interceptor {
        val queryInterceptor: Interceptor = Interceptor { chain ->
            val originalRequest: Request = chain.request()
            val originalHttpUrl: HttpUrl = originalRequest.url

            val newHttpUrl: HttpUrl =
                originalHttpUrl.newBuilder().addQueryParameter(
                    queryKey,
                    queryValue
                ).build()
            val newRequest: Request = originalRequest.newBuilder().url(newHttpUrl).build()

            chain.proceed(newRequest)
        }
        return queryInterceptor
    }

    inline fun <reified T> getRetrofitService(
        queryInterceptor: Interceptor?,
        baseUrl: String
    ): T {
        return getRetrofit(queryInterceptor, baseUrl).create(T::class.java)
    }
}
