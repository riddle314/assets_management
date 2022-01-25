package com.example.assetmanagement.usecases.transactions_activity.selection_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.assetmanagement.domain.DomainRepository
import com.example.assetmanagement.domain.model.ResponseDomainModel
import com.example.assetmanagement.domain.model.SelectionListResultDomainModel
import com.example.assetmanagement.usecases.common.LoadingAndErrorViewModel
import com.example.assetmanagement.usecases.common.model.Event
import com.example.assetmanagement.usecases.transactions_activity.selection_list.model.SearchTypeModel
import com.example.assetmanagement.usecases.transactions_activity.selection_list.model.SelectionListResultModel
import com.example.assetmanagement.usecases.transactions_activity.selection_list.transformers.SearchTypeTransformers
import com.example.assetmanagement.usecases.transactions_activity.selection_list.transformers.SelectionListResultDataTransformers
import com.example.assetmanagement.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectionListViewModel @Inject constructor(private var repository: DomainRepository) :
    LoadingAndErrorViewModel() {

    lateinit var searchType: SearchTypeModel

    private var mSearchQuery: String = Utils.EMPTY_STRING

    // data for presentation

    private val mItemsList: MutableLiveData<List<SelectionListResultModel>> by lazy {
        MutableLiveData<List<SelectionListResultModel>>()
    }

    val itemsList: LiveData<List<SelectionListResultModel>>
        get() = mItemsList

    private val mIsItemsListVisible: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val isItemsListVisible: LiveData<Boolean>
        get() = mIsItemsListVisible

    // data for navigation

    private val mReturnSelectionEvent: MutableLiveData<Event<SelectionListResultModel>> by lazy {
        MutableLiveData<Event<SelectionListResultModel>>()
    }

    val returnSelectionEvent: LiveData<Event<SelectionListResultModel>>
        get() = mReturnSelectionEvent


    // decision functions

    override fun loadData() {
        fetchListItems()
    }

    private fun fetchListItems() {
        // clear the search query
        mSearchQuery = Utils.EMPTY_STRING
        fetchListItemsForSearchQuery(mSearchQuery)
    }

    fun fetchListItemsForSearchQuery(searchQuery: String) {
        mSearchQuery = searchQuery
        setLoadingState()
        clearErrorMessage()
        viewModelScope.launch {
            val result = if (mSearchQuery.isEmpty()) {
                repository.getAllSearchItems(
                    SearchTypeTransformers.transformToSearchTypeDomain(
                        searchType
                    )
                )
            } else {
                repository.getSearchItemsForQuery(
                    mSearchQuery,
                    SearchTypeTransformers.transformToSearchTypeDomain(searchType)
                )
            }
            listItemsResponse(result)
        }
    }

    private fun listItemsResponse(
        result: ResponseDomainModel<List<SelectionListResultDomainModel>>
    ) {
        mIsLoadingViewVisible.value = false
        if (result.isSuccess) {
            mItemsList.value =
                SelectionListResultDataTransformers.transform(result.responseData)
            setContentState()
        } else {
            setErrorState()
            mErrorMessage.value = result.errorMessage
        }
    }

    fun searchItemSelected(selectionListResultModel: SelectionListResultModel) {
        mReturnSelectionEvent.value = Event(selectionListResultModel)
    }

    override fun errorViewClicked() {
        fetchListItemsForSearchQuery(mSearchQuery)
    }

    // helper functions
    fun getSearchItemsListSize(): Int {
        return if (mItemsList.value != null && mItemsList.value is List<SelectionListResultModel>) {
            (mItemsList.value as List<SelectionListResultModel>).size
        } else {
            Utils.ZERO_NUM
        }
    }

    private fun setLoadingState() {
        mIsItemsListVisible.value = false
        mIsLoadingViewVisible.value = true
        mIsErrorViewVisible.value = false
    }

    private fun setErrorState() {
        mIsItemsListVisible.value = false
        mIsLoadingViewVisible.value = false
        mIsErrorViewVisible.value = true
    }

    private fun setContentState() {
        mIsItemsListVisible.value = true
        mIsLoadingViewVisible.value = false
        mIsErrorViewVisible.value = false
    }

}