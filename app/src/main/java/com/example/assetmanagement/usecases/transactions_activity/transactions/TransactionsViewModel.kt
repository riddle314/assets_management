package com.example.assetmanagement.usecases.transactions_activity.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.assetmanagement.domain.model.TransactionItemResponseDomainModel
import com.example.assetmanagement.domain.Repository
import com.example.assetmanagement.domain.di.DataAnalysisRepository
import com.example.assetmanagement.usecases.transactions_activity.transactions.model.TransactionItemModel
import com.example.assetmanagement.usecases.transactions_activity.transactions.transformers.TransactionsDataTransformer
import com.example.assetmanagement.utils.Event
import com.example.assetmanagement.utils.LoadingAndErrorViewModel
import com.example.assetmanagement.domain.model.ResponseDomainModel
import com.example.assetmanagement.domain.di.DemoRepository
import com.example.assetmanagement.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(@DataAnalysisRepository private var repository: Repository): LoadingAndErrorViewModel() {

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
        mIsTransactionsListVisible.value = false
        mIsLoadingViewVisible.value = true
        mIsErrorViewVisible.value = false
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
            mIsTransactionsListVisible.value = true
            mTransactionsList.value =
                TransactionsDataTransformer.transform(result.responseData)
        } else {
            mIsErrorViewVisible.value = true
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
}