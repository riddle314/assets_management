package com.example.assetmanagement.usecases.transactions_activity.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.assetmanagement.domain.DomainRepository
import com.example.assetmanagement.domain.model.ResponseDomainModel
import com.example.assetmanagement.domain.model.TransactionItemResponseDomainModel
import com.example.assetmanagement.usecases.common.LoadingAndErrorViewModel
import com.example.assetmanagement.usecases.common.model.Event
import com.example.assetmanagement.usecases.transactions_activity.transactions.model.TransactionItemModel
import com.example.assetmanagement.usecases.transactions_activity.transactions.transformers.TransactionsDataTransformer
import com.example.assetmanagement.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(private var repository: DomainRepository) :
    LoadingAndErrorViewModel() {

    private var mSearchQuery: String = Utils.EMPTY_STRING

    // data for presentation

    private val mTransactionsList: MutableLiveData<List<TransactionItemModel>> by lazy {
        MutableLiveData<List<TransactionItemModel>>()
    }

    val transactionsList: LiveData<List<TransactionItemModel>>
        get() = mTransactionsList

    private val mIsTransactionsListVisible: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val isTransactionsListVisible: LiveData<Boolean>
        get() = mIsTransactionsListVisible

    private val mIsNoContentViewVisible: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val isNoContentViewVisible: LiveData<Boolean>
        get() = mIsNoContentViewVisible

    // data for navigation

    private val mNavigateToAddTransaction: MutableLiveData<Event<Boolean>> by lazy {
        MutableLiveData<Event<Boolean>>()
    }

    val navigateToAddTransaction: LiveData<Event<Boolean>>
        get() = mNavigateToAddTransaction

    private val mNavigateToTransactionDetails: MutableLiveData<Event<Int>> by lazy {
        MutableLiveData<Event<Int>>()
    }

    val navigateToTransactionDetails: LiveData<Event<Int>>
        get() = mNavigateToTransactionDetails

    // decision functions

    fun fetchTransactions() {
        // clear the search query
        mSearchQuery = Utils.EMPTY_STRING
        fetchTransactionsForSearchQuery(mSearchQuery)
    }

    fun fetchTransactionsForSearchQuery(searchQuery: String) {
        mSearchQuery = searchQuery
        setLoadingState()
        clearErrorMessage()
        viewModelScope.launch {
            val result = if (mSearchQuery.isEmpty()) {
                repository.getAllTransactions()
            } else {
                repository.getTransactionsForQuery(mSearchQuery)
            }
            transactionsResponse(result)
        }
    }

    private fun transactionsResponse(
        result: ResponseDomainModel<List<TransactionItemResponseDomainModel>>
    ) {
        mIsLoadingViewVisible.value = false
        if (result.isSuccess) {
            mTransactionsList.value =
                TransactionsDataTransformer.transform(result.responseData)
            if (mTransactionsList.value.isNullOrEmpty()) {
                setNoContentState()
            } else {
                setContentState()
            }
        } else {
            setErrorState()
            mErrorMessage.value = result.errorMessage
        }
    }

    fun addTransactionButtonClicked() {
        mNavigateToAddTransaction.value = Event(true)
    }

    fun transactionDetailsButtonClicked(transactionId: Int) {
        mNavigateToTransactionDetails.value = Event(transactionId)
    }

    override fun errorViewClicked() {
        fetchTransactionsForSearchQuery(mSearchQuery)
    }

    // helper functions

    fun getTransactionsListSize(): Int {
        return if (mTransactionsList.value != null && mTransactionsList.value is List<TransactionItemModel>) {
            (mTransactionsList.value as List<TransactionItemModel>).size
        } else {
            Utils.ZERO_NUM
        }
    }

    private fun setLoadingState() {
        mIsTransactionsListVisible.value = false
        mIsNoContentViewVisible.value = false
        mIsLoadingViewVisible.value = true
        mIsErrorViewVisible.value = false
    }

    private fun setErrorState() {
        mIsTransactionsListVisible.value = false
        mIsNoContentViewVisible.value = false
        mIsLoadingViewVisible.value = false
        mIsErrorViewVisible.value = true
    }

    private fun setContentState() {
        mIsTransactionsListVisible.value = true
        mIsNoContentViewVisible.value = false
        mIsLoadingViewVisible.value = false
        mIsErrorViewVisible.value = false
    }

    private fun setNoContentState() {
        mIsTransactionsListVisible.value = false
        mIsNoContentViewVisible.value = true
        mIsLoadingViewVisible.value = false
        mIsErrorViewVisible.value = false
    }
}