package com.example.assetmanagement.usecases.transactionsActivity.transactionDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.assetmanagement.common.LoadingAndErrorViewModel
import com.example.assetmanagement.common.model.Event
import com.example.assetmanagement.usecases.transactionsActivity.transactionDetails.model.TransactionDetailsModel
import com.example.assetmanagement.usecases.transactionsActivity.transactionDetails.transformers.TransactionsDetailsDataTransformer
import com.example.domain.DomainRepository
import com.example.domain.model.ResponseDomainModel
import com.example.domain.model.TransactionDetailsResponseDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailsViewModel @Inject constructor(private var repository: DomainRepository) :
    LoadingAndErrorViewModel() {

    // data for presentation

    // the transaction Id we need to identify the transaction details
    var transactionId: Int = -1

    private val mTransactionDetails: MutableLiveData<TransactionDetailsModel> =
        MutableLiveData<TransactionDetailsModel>()

    val transactionDetails: LiveData<TransactionDetailsModel>
        get() = mTransactionDetails

    private val mIsTransactionDetailsVisible: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    val isTransactionDetailsVisible: LiveData<Boolean>
        get() = mIsTransactionDetailsVisible

    // data for navigation

    private val mNavigateToTransactions: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()

    val navigateToTransactions: LiveData<Event<Boolean>>
        get() = mNavigateToTransactions

    // decision functions

    override fun loadData() {
        fetchTransactionDetails()
    }

    private fun fetchTransactionDetails() {
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