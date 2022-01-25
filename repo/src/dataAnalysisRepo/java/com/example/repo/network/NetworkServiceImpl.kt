package com.example.repo.network

import android.content.Context
import com.example.repo.R
import com.example.repo.model.ResponseDataModel
import com.example.repo.model.SelectionListResultDataModel
import com.example.repo.network.mechanism.AmdorenApiEndpointService
import com.example.repo.network.mechanism.model.CurrenciesAPI
import com.example.repo.transformers.ApiToDataTransformers
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import javax.inject.Inject

class NetworkServiceImpl @Inject constructor(@ApplicationContext private val context: Context) :
    NetworkService {


    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface NetworkServiceImplEntryPoint {
        fun getAmdorenApiEndpointService(): AmdorenApiEndpointService
    }

    private val amdorenApiEndpointService: AmdorenApiEndpointService =
        EntryPointAccessors.fromApplication(
            context,
            NetworkServiceImplEntryPoint::class.java
        ).getAmdorenApiEndpointService()

    override fun getAllCurrencies(): ResponseDataModel<List<SelectionListResultDataModel>> {
        return try {
            val response: Response<CurrenciesAPI> =
                amdorenApiEndpointService.getCurrencies().execute()
            val body = response.body()
            if (response.isSuccessful && body != null
                && body.error == 0 && !body.currencies.isNullOrEmpty()
            ) {
                val result: List<SelectionListResultDataModel> = ApiToDataTransformers.transform(
                    body.currencies
                )
                ResponseDataModel(result, true, null)
            } else {
                val errorMessage = if (body != null && !body.errorMessage.isNullOrEmpty()) {
                    body.errorMessage
                } else {
                    response.message()
                }

                ResponseDataModel(
                    arrayListOf(),
                    false,
                    getError(errorMessage, context)
                )
            }
        } catch (e: Exception) {
            ResponseDataModel(arrayListOf(), false, getError(e, context))
        }
    }

    private fun getError(e: Exception, context: Context) =
        e.message ?: context.getString(R.string.general_error)

    private fun getError(message: String?, context: Context) =
        message ?: context.getString(R.string.general_error)
}