package com.example.assetmanagement.usecases.transactions_activity.transaction_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.assetmanagement.domain.DomainRepository
import com.example.assetmanagement.domain.model.ResponseDomainModel
import com.example.assetmanagement.domain.model.TransactionDetailsResponseDomainModel
import com.example.assetmanagement.usecases.common.LoadingAndErrorViewModel
import com.example.assetmanagement.usecases.common.model.Event
import com.example.assetmanagement.usecases.transactions_activity.transaction_details.model.TransactionDetailsModel
import com.example.assetmanagement.usecases.transactions_activity.transaction_details.transformers.TransactionsDetailsDataTransformer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailsViewModel @Inject constructor(private var repository: DomainRepository) :
    LoadingAndErrorViewModel() {

    // data for presentation

    // the transaction Id we need to identify the transaction details
    var transactionId: Int = -1

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

    override fun loadData() {
        fetchTransactionDetails()
    }

    fun fetchTransactionDetails() {
        setLoadingState()
        clearErrorMessage()
        viewModelScope.launch {
            val result = repository.getTransactionDetails(transactionId)
            transactionDetailsResponse(result)
        }
    }

    private fun transactionDetailsResponse(
        result: ResponseDomainModel<TransactionDetailsResponseDomainModel?>
    ) {
        if (result.isSuccess && result.responseData != null) {
            setContentState()
            mTransactionDetails.value =
                TransactionsDetailsDataTransformer.transform(result.responseData!!)
        } else {
            setErrorState()
            mErrorMessage.value = result.errorMessage
        }
    }

    fun deleteTransaction() {
        setLoadingState()
        clearErrorMessage()
        viewModelScope.launch {
            val result = repository.deleteTransaction(transactionId)
            deleteTransactionResponse(result)
        }
    }

    private fun deleteTransactionResponse(result: ResponseDomainModel<String?>) {
        if (result.isSuccess) {
            mNavigateToTransactions.value = Event(true)
        } else {
            setErrorState()
            mErrorMessage.value = result.errorMessage
        }
    }

    // helper functions
    override fun errorViewClicked() {
        fetchTransactionDetails()
    }

    private fun setLoadingState() {
        mIsTransactionDetailsVisible.value = false
        mIsLoadingViewVisible.value = true
        mIsErrorViewVisible.value = false
    }

    private fun setErrorState() {
        mIsTransactionDetailsVisible.value = false
        mIsLoadingViewVisible.value = false
        mIsErrorViewVisible.value = true
    }

    private fun setContentState() {
        mIsTransactionDetailsVisible.value = true
        mIsLoadingViewVisible.value = false
        mIsErrorViewVisible.value = false
    }
}