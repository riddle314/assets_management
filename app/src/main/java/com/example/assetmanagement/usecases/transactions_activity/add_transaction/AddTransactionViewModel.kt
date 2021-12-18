package com.example.assetmanagement.usecases.transactions_activity.add_transaction

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.assetmanagement.domain.Repository
import com.example.assetmanagement.domain.di.DataAnalysisRepository
import com.example.assetmanagement.domain.model.ResponseDomainModel
import com.example.assetmanagement.domain.model.TransactionDetailsResponseDomainModel
import com.example.assetmanagement.usecases.common.LoadingAndErrorViewModel
import com.example.assetmanagement.usecases.common.model.AssetTypeModel
import com.example.assetmanagement.usecases.common.model.Event
import com.example.assetmanagement.usecases.common.model.TransactionTypeModel
import com.example.assetmanagement.usecases.transactions_activity.add_transaction.model.AddTransactionModel
import com.example.assetmanagement.usecases.transactions_activity.add_transaction.transformers.AddTransactionDataTransformer
import com.example.assetmanagement.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(@DataAnalysisRepository private var repository: Repository) :
    LoadingAndErrorViewModel() {

    companion object {
        private const val DEFAULT_VALUE = -1
    }

    // data for presentation
    var transactionId: Int = DEFAULT_VALUE

    private val mAddTransactionModel: MutableLiveData<AddTransactionModel> by lazy {
        MutableLiveData<AddTransactionModel>()
    }

    val addTransactionModel: LiveData<AddTransactionModel>
        get() = mAddTransactionModel

    private val mIsTransactionFieldsVisible: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val isTransactionFieldsVisible: LiveData<Boolean>
        get() = mIsTransactionFieldsVisible

    private val mDateOfTransaction: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val dateOfTransaction: LiveData<String>
        get() = mDateOfTransaction

    // data for navigation

    private val mOpenValidationDialog: MutableLiveData<Event<Boolean>> by lazy {
        MutableLiveData<Event<Boolean>>()
    }

    val openValidationDialog: LiveData<Event<Boolean>>
        get() = mOpenValidationDialog

    private val mNavigateToTransactions: MutableLiveData<Event<Boolean>> by lazy {
        MutableLiveData<Event<Boolean>>()
    }

    val navigateToTransactions: LiveData<Event<Boolean>>
        get() = mNavigateToTransactions

    private val mOpenDatePickerDialog: MutableLiveData<Event<Long>> by lazy {
        MutableLiveData<Event<Long>>()
    }

    val openDatePickerDialog: LiveData<Event<Long>>
        get() = mOpenDatePickerDialog

    // lamda function to decide the action after the error clicked
    private lateinit var onErrorClickedActionLamdaFun: () -> Unit

    // decision functions

    fun loadData() {
        if (isEditTransaction()) {
            // fetch transaction info for edit
            fetchTransactionInfo(transactionId)
        } else {
            // create an empty Transaction model and set the default day to today
            mAddTransactionModel.value = AddTransactionModel(transactionId)
            updateDate(Calendar.getInstance().timeInMillis)
            setContentState()
        }
    }

    private fun fetchTransactionInfo(transactionId: Int) {
        setLoadingState()
        clearErrorMessage()
        viewModelScope.launch {
            val result = repository.getTransactionDetails(transactionId)
            transactionInfoResponse(result)
        }
    }

    private fun transactionInfoResponse(
        result: ResponseDomainModel<TransactionDetailsResponseDomainModel?>
    ) {
        if (result.isSuccess && result.responseData != null) {
            setContentState()
            mAddTransactionModel.value =
                AddTransactionDataTransformer.transformToResponse(result.responseData!!)
            updateDate(mAddTransactionModel.value!!.date)
        } else {
            onErrorClickedActionLamdaFun = { fetchTransactionInfo(transactionId) }
            setErrorState()
            mErrorMessage.value = result.errorMessage
        }
    }

    override fun errorViewClicked() {
        onErrorClickedActionLamdaFun.invoke()
    }

    fun isEditTransaction(): Boolean {
        return transactionId != DEFAULT_VALUE
    }

    fun saveTransaction() {
        mAddTransactionModel.value?.let {
            if (isDataValid(it)) {
                setLoadingState()
                clearErrorMessage()
                viewModelScope.launch {
                    val result = if (isEditTransaction()) {
                        repository.editTransaction(
                            AddTransactionDataTransformer.transformToEditRequest(it)
                        )
                    } else {
                        repository.addTransaction(
                            AddTransactionDataTransformer.transformToAddRequest(it)
                        )
                    }
                    saveTransactionResponse(result)
                }
            } else {
                mOpenValidationDialog.value = Event(true)
            }
        }
    }

    private fun isDataValid(addTransactionModel: AddTransactionModel?): Boolean {
        return addTransactionModel != null
                && addTransactionModel.assetsName.isNotEmpty()
                && addTransactionModel.quantity.isNotEmpty()
                && addTransactionModel.price.isNotEmpty()
                && addTransactionModel.priceCurrency.isNotEmpty()
    }

    private fun saveTransactionResponse(
        result: ResponseDomainModel<String?>
    ) {
        if (result.isSuccess) {
            setContentState()
            mNavigateToTransactions.value = Event(true)
        } else {
            onErrorClickedActionLamdaFun = { saveTransaction() }
            setErrorState()
            mErrorMessage.value = result.errorMessage
        }
    }

    fun openDatePicker() {
        mAddTransactionModel.value?.let {
            mOpenDatePickerDialog.value = Event(it.date)
        }
    }

    private fun setLoadingState() {
        mIsTransactionFieldsVisible.value = false
        mIsLoadingViewVisible.value = true
        mIsErrorViewVisible.value = false
    }

    private fun setErrorState() {
        mIsTransactionFieldsVisible.value = false
        mIsLoadingViewVisible.value = false
        mIsErrorViewVisible.value = true
    }

    private fun setContentState() {
        mIsTransactionFieldsVisible.value = true
        mIsLoadingViewVisible.value = false
        mIsErrorViewVisible.value = false
    }

    fun getAssetTypeListOfValues(context: Context): List<String> {
        return AssetTypeModel.listOfStringValues(context)
    }

    fun getTransactionTypeListOfValues(context: Context): List<String> {
        return TransactionTypeModel.listOfStringValues(context)
    }

    fun updateDate(milliseconds: Long) {
        mAddTransactionModel.value?.let {
            it.date = milliseconds
            mDateOfTransaction.value = Utils.getDateToString(milliseconds)
        }
    }
}