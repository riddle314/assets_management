package com.example.assetmanagement.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * This is a ViewModel to use when we want to add loading and error layout logic
 */
abstract class LoadingAndErrorViewModel : ViewModel() {

    // data for presentation

    var isDataAlreadyLoaded: Boolean = false

    protected val mIsLoadingViewVisible: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    val isLoadingViewVisible: LiveData<Boolean>
        get() = mIsLoadingViewVisible

    protected val mIsErrorViewVisible: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    val isErrorViewVisible: LiveData<Boolean>
        get() = mIsErrorViewVisible

    protected val mErrorMessage: MutableLiveData<String> = MutableLiveData<String>()

    val errorMessage: LiveData<String>
        get() = mErrorMessage

    // decision functions

    abstract fun errorViewClicked();

    // function to load / fetch data only one time
    fun firstTimeLoadData() {
        if (!isDataAlreadyLoaded) {
            isDataAlreadyLoaded = true
            loadData()
        }
    }

    abstract fun loadData();

    // helper functions

    protected fun clearErrorMessage() {
        mErrorMessage.value = Utils.EMPTY_STRING
    }
}