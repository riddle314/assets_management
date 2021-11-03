package com.example.assetmanagement.usecases.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assetmanagement.utils.Utils

/**
 * This is a ViewModel to use when we want to add loading and error layout logic
 */
abstract class LoadingAndErrorViewModel : ViewModel(){

    // data for presentation

    protected val mIsLoadingViewVisible: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val isLoadingViewVisible: LiveData<Boolean>
        get() = mIsLoadingViewVisible

    protected val mIsErrorViewVisible: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val isErrorViewVisible: LiveData<Boolean>
        get() = mIsErrorViewVisible

    protected val mErrorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val errorMessage: LiveData<String>
        get() = mErrorMessage

    // decision functions

    abstract fun errorViewClicked();

    // helper functions

    protected fun clearErrorMessage() {
        mErrorMessage.value = Utils.EMPTY_STRING
    }
}