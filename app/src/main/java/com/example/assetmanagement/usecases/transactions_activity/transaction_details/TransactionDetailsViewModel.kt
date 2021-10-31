package com.example.assetmanagement.usecases.transactions_activity.transaction_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.assetmanagement.domain.Repository
import com.example.assetmanagement.domain.di.DataAnalysisRepository
import com.example.assetmanagement.domain.di.DemoRepository
import com.example.assetmanagement.domain.model.ResponseDomainModel
import com.example.assetmanagement.domain.model.TransactionDetailsResponseDomainModel
import com.example.assetmanagement.usecases.transactions_activity.transaction_details.model.TransactionDetailsModel
import com.example.assetmanagement.usecases.transactions_activity.transaction_details.transformers.TransactionsDetailsDataTransformer
import com.example.assetmanagement.utils.Event
import com.example.assetmanagement.utils.LoadingAndErrorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailsViewModel @Inject constructor(@DataAnalysisRepository private var repository: Repository) :
    LoadingAndErrorViewModel() {

    // data for presentation

    // the transaction Id we need to identify the transaction details
    var transactionId: Int = 0

    private val mTransactionDetails: MutableLiveData<TransactionDetailsModel> by lazy {
        MutableLiveData<TransactionDetailsModel>()
    }

    val transactionDetails: LiveData<TransactionDetailsModel>
        get() = mTransactionDetails

    private val mIsTransactionDetailsVisible: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val isTransactionDetailsVisible: LiveData<Boolean>
        get() = mIsTransactionDetailsVisible

    // data for navigation

    private val mNavigateToTransactions: MutableLiveData<Event<Boolean>> by lazy {
        MutableLiveData<Event<Boolean>>()
    }

    val navigateToTransactions: LiveData<Event<Boolean>>
        get() = mNavigateToTransactions

    // decision functions

    fun fetchTransactionDetails() {
        mIsTransactionDetailsVisible.value = false
        mIsLoadingViewVisible.value = true
        mIsErrorViewVisible.value = false
        clearErrorMessage()
        viewModelScope.launch {
            val result = repository.getTransactionDetails(transactionId)
            transactionDetailsResponse(result)
        }
    }

    private fun transactionDetailsResponse(
        result: ResponseDomainModel<TransactionDetailsResponseDomainModel?>
    ) {
        mIsLoadingViewVisible.value = false
        if (result.isSuccess && result.responseData != null) {
            mIsTransactionDetailsVisible.value = true
            mTransactionDetails.value =
                TransactionsDetailsDataTransformer.transform(result.responseData!!)
        } else {
            mIsErrorViewVisible.value = true
            mErrorMessage.value = result.errorMessage
        }
    }

    fun deleteTransaction() {
        mIsTransactionDetailsVisible.value = false
        mIsLoadingViewVisible.value = true
        mIsErrorViewVisible.value = false
        clearErrorMessage()
        viewModelScope.launch {
            val result = repository.deleteTransaction(transactionId)
            deleteTransactionResponse(result)
        }
    }

    private fun deleteTransactionResponse(result: ResponseDomainModel<String?>) {
        mIsLoadingViewVisible.value = false
        if (result.isSuccess) {
            mNavigateToTransactions.value = Event(true)
        } else {
            mIsErrorViewVisible.value = true
            mErrorMessage.value = result.errorMessage
        }
    }

    // helper functions
    override fun errorViewClicked() {
        fetchTransactionDetails()
    }
}