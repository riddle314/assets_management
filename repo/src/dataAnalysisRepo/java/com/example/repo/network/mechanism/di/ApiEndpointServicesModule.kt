package com.example.repo.network.mechanism.di

import android.content.Context
import com.example.repo.R
import com.example.repo.network.mechanism.AmdorenApiEndpointService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiEndpointServicesModule {

    const val AMDOREN_API_KEY_QUERY = "api_key"

    @Provides
    @Singleton
    fun provideAmdorenApiEndpointService(@ApplicationContext context: Context): AmdorenApiEndpointService {

        // create interceptor for authentication query to OkHttpClient
        val addApiKeyQueryInterceptor: Interceptor = Interceptor { chain ->
            val originalRequest: Request = chain.request()
            val originalHttpUrl: HttpUrl = originalRequest.url

            val newHttpUrl: HttpUrl =
                originalHttpUrl.newBuilder().addQueryParameter(
                    AMDOREN_API_KEY_QUERY,
                    context.resources.getString(R.string.amdoren_api_key)
                ).build()

            val newRequest: Request = originalRequest.newBuilder().url(newHttpUrl).build()

            chain.proceed(newRequest)
        }

        // create interceptor for login
        val httpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(addApiKeyQueryInterceptor)
        okHttpClientBuilder.addNetworkInterceptor(httpLoggingInterceptor)
        val okHttpClient: OkHttpClient = okHttpClientBuilder.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.amdoren_base_url))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(AmdorenApiEndpointService::class.java)
    }

}