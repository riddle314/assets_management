package com.example.repo.network

import android.content.Context
import com.example.repo.R
import com.example.repo.model.ResponseDataModel
import com.example.repo.model.SelectionListResultDataModel
import com.example.repo.network.mechanism.apiServices.amdoren.AmdorenService
import com.example.repo.network.mechanism.apiServices.amdoren.AmdorenServiceProvider
import com.example.repo.network.mechanism.apiServices.amdoren.model.CurrenciesAPI
import com.example.repo.network.mechanism.apiServices.openExchangeRates.OpenExchangeRatesService
import com.example.repo.network.mechanism.apiServices.openExchangeRates.OpenExchangeRatesServiceProvider
import com.example.repo.transformers.ApiToDataTransformers
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject

class NetworkServiceImpl @Inject constructor(@ApplicationContext private val context: Context) :
    NetworkService {

    private var amdorenService: AmdorenService =
        AmdorenServiceProvider.provideService(context)

    private var openExchangeRatesService: OpenExchangeRatesService =
        OpenExchangeRatesServiceProvider.provideService(context)

    override fun getAllCurrencies(): ResponseDataModel<List<SelectionListResultDataModel>> {
        return try {
            getAllCurrenciesFromOpenExchangeRatesService()
        } catch (e: Exception) {
            ResponseDataModel(arrayListOf(), false, getError(e.message, context))
        }
    }

    private fun getAllCurrenciesFromAmdorenService(): ResponseDataModel<List<SelectionListResultDataModel>> {
        val response: Response<CurrenciesAPI> = amdorenService.getAllCurrencies().execute()
        val body = response.body()
        return if (response.isSuccessful && body != null
            && body.error == 0 && !body.currencies.isNullOrEmpty()
        ) {
            val result: List<SelectionListResultDataModel> = ApiToDataTransformers.transformList(
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
    }

    private fun getAllCurrenciesFromOpenExchangeRatesService(): ResponseDataModel<List<SelectionListResultDataModel>> {
        val response: Response<Map<String, String>> =
            openExchangeRatesService.getAllCurrencies().execute()
        val body = response.body()
        return if (response.isSuccessful && body != null) {
            val result: List<SelectionListResultDataModel> =
                ApiToDataTransformers.transformMap(body)
            ResponseDataModel(result, true, null)
        } else {
            val errorMessage = if (!response.isSuccessful) {
                response.message()
            } else {
                null
            }

            ResponseDataModel(
                arrayListOf(),
                false,
                getError(errorMessage, context)
            )
        }
    }

    private fun getError(message: String?, context: Context) =
        message ?: context.getString(R.string.general_error)
}