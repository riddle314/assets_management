package com.example.assetmanagement.usecases.transactionsActivity.addTransaction

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.assetmanagement.domain.DomainRepository
import com.example.assetmanagement.domain.model.ResponseDomainModel
import com.example.assetmanagement.domain.model.TransactionDetailsResponseDomainModel
import com.example.assetmanagement.common.LoadingAndErrorViewModel
import com.example.assetmanagement.common.model.AssetTypeModel
import com.example.assetmanagement.common.model.Event
import com.example.assetmanagement.common.model.TransactionTypeModel
import com.example.assetmanagement.usecases.transactionsActivity.addTransaction.model.AddTransactionModel
import com.example.assetmanagement.usecases.transactionsActivity.addTransaction.model.AssetModel
import com.example.assetmanagement.usecases.transactionsActivity.addTransaction.transformers.AddTransactionDataTransformer
import com.example.assetmanagement.usecases.transactionsActivity.selectionList.model.SelectionListInputModel
import com.example.assetmanagement.usecases.transactionsActivity.selectionList.transformers.SearchTypeTransformers
import com.example.assetmanagement.common.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(private var repository: DomainRepository) :
    LoadingAndErrorViewModel() {

    companion object {
        private const val DEFAULT_VALUE = -1
        const val SELECT_CURRENCY_BUNDLE_KEY = "SELECT_CURRENCY_BUNDLE_KEY"
        const val SELECT_ASSET_NAME_BUNDLE_KEY = "SELECT_ASSET_NAME_BUNDLE_KEY"
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

    private val mOpenSelectionList: MutableLiveData<Event<SelectionListInputModel>> by lazy {
        MutableLiveData<Event<SelectionListInputModel>>()
    }

    val openSelectionList: LiveData<Event<SelectionListInputModel>>
        get() = mOpenSelectionList

    // lambda function to decide the action when the error clicked
    private lateinit var onErrorClickedActionLambdaFun: () -> Unit

    // decision functions

    override fun loadData() {
        if (isEditTransaction()) {
            // fetch transaction info for edit
            fetchTransactionInfo(transactionId)
        } else {
            // create an empty Transaction model and set the default day to today
            mAddTransactionModel.value = AddTransactionModel(transactionId).apply {
                date = Calendar.getInstance().timeInMillis
            }
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
        } else {
            onErrorClickedActionLambdaFun = { fetchTransactionInfo(transactionId) }
            setErrorState()
            mErrorMessage.value = result.errorMessage
        }
    }

    override fun errorViewClicked() {
        onErrorClickedActionLambdaFun.invoke()
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
                && addTransactionModel.assetModel.assetName.isNotEmpty()
                && addTransactionModel.assetModel.assetType == addTransactionModel.assetType
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
            onErrorClickedActionLambdaFun = { saveTransaction() }
            setErrorState()
            mErrorMessage.value = result.errorMessage
        }
    }

    fun openDatePicker() {
        mAddTransactionModel.value?.let {
            mOpenDatePickerDialog.value = Event(it.date)
        }
    }

    fun openAssetNameSelectionList(assetType: AssetTypeModel) {
        mOpenSelectionList.value = Event(
            SelectionListInputModel(
                SELECT_ASSET_NAME_BUNDLE_KEY,
                SearchTypeTransformers.transformToSearchTypeModel(assetType)
            )
        )
    }

    fun openCurrencySelectionList(assetType: AssetTypeModel) {
        mOpenSelectionList.value = Event(
            SelectionListInputModel(
                SELECT_CURRENCY_BUNDLE_KEY,
                SearchTypeTransformers.transformToSearchTypeModel(assetType)
            )
        )
    }

    fun getAssetTypeListOfValues(context: Context): List<String> {
        return AssetTypeModel.listOfStringValues(context)
    }

    fun getTransactionTypeListOfValues(context: Context): List<String> {
        return TransactionTypeModel.listOfStringValues(context)
    }

    fun updateDate(milliseconds: Long) {
        mAddTransactionModel.value?.let {
            mAddTransactionModel.value = copyAddTransactionModel(it).apply {
                this.date = milliseconds
            }
        }
    }

    fun updateCurrency(currency: String) {
        mAddTransactionModel.value?.let {
            it.priceCurrency = currency
        }
    }

    fun updateAssetName(assetModel: AssetModel) {
        mAddTransactionModel.value?.let {
            it.assetModel = assetModel
        }
    }

    fun getAssetTypePosition(assetType: AssetTypeModel?): Int {
        return AssetTypeModel.getPosition(assetType)
    }

    fun updateAssetType(position: Int) {
        mAddTransactionModel.value?.let {
            mAddTransactionModel.value = copyAddTransactionModel(it).apply {
                assetType = AssetTypeModel.getAssetTypeModel(position)
                if (assetModel.assetType != assetType) {
                    assetModel = AssetModel(Utils.EMPTY_STRING, assetType)
                }
            }
        }
    }

    // a function to copy AddTransactionModel to a new object
    private fun copyAddTransactionModel(source: AddTransactionModel): AddTransactionModel {
        return AddTransactionModel(
            source.transactionId,
            source.assetModel,
            source.quantity,
            source.price,
            source.priceCurrency,
            source.date,
            source.assetType,
            source.transactionType
        )
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
}