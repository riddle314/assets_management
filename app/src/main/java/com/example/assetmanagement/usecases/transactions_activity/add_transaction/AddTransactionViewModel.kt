package com.example.assetmanagement.usecases.transactions_activity.add_transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.assetmanagement.domain.Repository
import com.example.assetmanagement.domain.di.DataAnalysisRepository
import com.example.assetmanagement.domain.model.ResponseDomainModel
import com.example.assetmanagement.domain.model.TransactionDetailsResponseDomainModel
import com.example.assetmanagement.usecases.transactions_activity.add_transaction.model.AddTransactionModel
import com.example.assetmanagement.usecases.transactions_activity.add_transaction.transformers.AddTransactionDataTransformer
import com.example.assetmanagement.utils.Event
import com.example.assetmanagement.utils.LoadingAndErrorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(@DataAnalysisRepository private var repository: Repository) :
    LoadingAndErrorViewModel() {

    // data for presentation
    var transactionId: Int = -1

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

    // lamda function to decide the action after the error clicked
    private lateinit var onErrorClickedActionLamdaFun: () -> Unit

    // decision functions

    fun prefillWithData() {
        if (isEditTransaction()) {
            // fetch transaction info for edit
            fetchTransactionInfo(transactionId)
        } else {
            // create an empty Transaction model
            mAddTransactionModel.value = AddTransactionModel(transactionId)
            mIsLoadingViewVisible.value = false
            mIsTransactionFieldsVisible.value = true
            mIsErrorViewVisible.value = false
        }
    }

    private fun fetchTransactionInfo(transactionId: Int) {
        mIsTransactionFieldsVisible.value = false
        mIsLoadingViewVisible.value = true
        mIsErrorViewVisible.value = false
        clearErrorMessage()
        viewModelScope.launch {
            val result = repository.getTransactionDetails(transactionId)
            transactionInfoResponse(result)
        }
    }

    private fun transactionInfoResponse(
        result: ResponseDomainModel<TransactionDetailsResponseDomainModel?>
    ) {
        mIsLoadingViewVisible.value = false
        if (result.isSuccess && result.responseData != null) {
            mIsTransactionFieldsVisible.value = true
            mAddTransactionModel.value =
                AddTransactionDataTransformer.transformToResponse(result.responseData!!)
        } else {
            onErrorClickedActionLamdaFun = { fetchTransactionInfo(transactionId) }
            mIsErrorViewVisible.value = true
            mErrorMessage.value = result.errorMessage
        }
    }

    override fun errorViewClicked() {
        onErrorClickedActionLamdaFun.invoke()
    }

    fun isEditTransaction(): Boolean {
        return transactionId != -1
    }

    fun saveTransaction() {
        if (dataIsValid(mAddTransactionModel.value)) {
            mIsTransactionFieldsVisible.value = false
            mIsLoadingViewVisible.value = true
            mIsErrorViewVisible.value = false
            clearErrorMessage()
            viewModelScope.launch {
                val result = if (isEditTransaction()) {
                    repository.editTransaction(
                        AddTransactionDataTransformer.transformToEditRequest(addTransactionModel.value!!)
                    )
                } else {
                    repository.addTransaction(
                        AddTransactionDataTransformer.transformToAddRequest(
                            addTransactionModel.value!!
                        )
                    )
                }
                saveTransactionResponse(result)
            }
        } else {
            mOpenValidationDialog.value = Event(true)
        }
    }

    private fun dataIsValid(addTransactionModel: AddTransactionModel?): Boolean {
        return addTransactionModel != null
                && !addTransactionModel.assetsName.isNullOrEmpty()
                && !addTransactionModel.quantity.isNullOrEmpty()
                && !addTransactionModel.price.isNullOrEmpty()
                && !addTransactionModel.priceCurrency.isNullOrEmpty()
                && !addTransactionModel.date.isNullOrEmpty()
                && !addTransactionModel.assetType.isNullOrEmpty()
                && !addTransactionModel.transactionType.isNullOrEmpty()
    }

    private fun saveTransactionResponse(
        result: ResponseDomainModel<String?>
    ) {
        mIsLoadingViewVisible.value = false
        if (result.isSuccess) {
            mIsTransactionFieldsVisible.value = true
            mNavigateToTransactions.value = Event(true)
        } else {
            onErrorClickedActionLamdaFun = { saveTransaction() }
            mIsErrorViewVisible.value = true
            mErrorMessage.value = result.errorMessage
        }
    }
}