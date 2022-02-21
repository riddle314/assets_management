package com.example.assetmanagement.usecases.transactionsActivity.addTransaction


import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.assetmanagement.R
import com.example.assetmanagement.databinding.FragmentAddTransactionBinding
import com.example.assetmanagement.common.ConfirmationDialogFragment
import com.example.assetmanagement.common.model.AssetTypeModel
import com.example.assetmanagement.common.model.Event
import com.example.assetmanagement.usecases.transactionsActivity.addTransaction.model.AssetModel
import com.example.assetmanagement.usecases.transactionsActivity.selectionList.SelectionListFragment
import com.example.assetmanagement.usecases.transactionsActivity.selectionList.model.SearchTypeModel
import com.example.assetmanagement.usecases.transactionsActivity.selectionList.model.SelectionListInputModel
import com.example.assetmanagement.usecases.transactionsActivity.selectionList.model.SelectionListResultModel
import com.example.assetmanagement.usecases.transactionsActivity.transactionDetails.TransactionDetailsFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


/**
 * A fragment to add a transaction
 */
@AndroidEntryPoint
class AddTransactionFragment : Fragment(R.layout.fragment_add_transaction) {

    private val viewModel: AddTransactionViewModel by viewModels()
    private lateinit var binding: FragmentAddTransactionBinding

    private val args: TransactionDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
    }

    private fun setupUI(view: View) {
        // setup view model
        viewModel.transactionId = args.transactionId

        // setup data binding
        binding = FragmentAddTransactionBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        if (activity is AppCompatActivity) {
            val appCompatActivity = activity as AppCompatActivity
            if (viewModel.isEditTransaction()) {
                appCompatActivity.supportActionBar?.title =
                    getString(R.string.edit_transaction_title)
            } else {
                appCompatActivity.supportActionBar?.title =
                    getString(R.string.add_transaction_title)
            }
        }

        // setup asset type spinner
        binding.assetTypeSpinner.adapter = context?.let {
            ArrayAdapter<String>(
                it,
                R.layout.spinner_item, viewModel.getAssetTypeListOfValues(it)
            )
        }

        // setup transaction type spinner
        binding.transactionTypeSpinner.adapter = context?.let {
            ArrayAdapter<String>(
                it,
                R.layout.spinner_item, viewModel.getTransactionTypeListOfValues(it)
            )
        }

        // set the input types of TextInputEditTexts to decimal numbers
        binding.quantity.inputType = TYPE_CLASS_NUMBER or TYPE_NUMBER_FLAG_DECIMAL
        binding.price.inputType = TYPE_CLASS_NUMBER or TYPE_NUMBER_FLAG_DECIMAL

        setupListenersAndObservers()
        viewModel.firstTimeLoadData()
    }

    private fun setupListenersAndObservers() {
        val navigateToTransactionsObserver = Observer<Event<Boolean>> {
            if (!it.isEventHandled) {
                it.handleEvent()
                navigateToTransactionsScreen()
            }
        }
        viewModel.navigateToTransactions.observe(viewLifecycleOwner, navigateToTransactionsObserver)

        val openValidationDialogObserver = Observer<Event<Boolean>> {
            if (!it.isEventHandled) {
                it.handleEvent()
                createValidationDialog()
            }
        }
        viewModel.openValidationDialog.observe(viewLifecycleOwner, openValidationDialogObserver)

        val openDatePickerDialogObserver = Observer<Event<Long>> {
            if (!it.isEventHandled) {
                openDatePickerDialog(it.getDataAndHandleEvent())
            }
        }
        viewModel.openDatePickerDialog.observe(viewLifecycleOwner, openDatePickerDialogObserver)

        // open the SelectionListFragment
        val openSelectionListFragmentObserver = Observer<Event<SelectionListInputModel>> {
            if (!it.isEventHandled) {
                openSearchListFragment(it.getDataAndHandleEvent())
            }
        }
        viewModel.openSelectionList.observe(
            viewLifecycleOwner,
            openSelectionListFragmentObserver
        )

        binding.assetTypeSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.updateAssetType(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        // handle the result from the SearchListFragment
        parentFragmentManager.setFragmentResultListener(SelectionListFragment.RESULT_REQUEST_KEY,
            this,
            { requestKey, result ->
                result.getParcelable<SelectionListResultModel>(AddTransactionViewModel.SELECT_CURRENCY_BUNDLE_KEY)
                    ?.let { viewModel.updateCurrency(it.name) }

                result.getParcelable<SelectionListResultModel>(AddTransactionViewModel.SELECT_ASSET_NAME_BUNDLE_KEY)
                    ?.let {
                        val assetTypeModel = when (it.searchTypeModel) {
                            SearchTypeModel.CRYPTO -> AssetTypeModel.CRYPTO
                            SearchTypeModel.CURRENCY -> AssetTypeModel.CURRENCY
                            SearchTypeModel.STOCK -> AssetTypeModel.STOCK
                        }
                        viewModel.updateAssetName(AssetModel(it.name, assetTypeModel))
                    }
            }
        )

        // Handle the back button and up button event
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, true) {
            createBackConfirmationDialog()
        }
    }

    private fun navigateToTransactionsScreen() {
        val action =
            AddTransactionFragmentDirections.actionAddTransactionFragmentToTransactionsFragment()
        findNavController().navigate(action)
    }

    private fun createValidationDialog() {
        val validationDialog = ConfirmationDialogFragment()
        validationDialog.setMessage(getString(R.string.validation_dialog_message))
        validationDialog.setPositiveButton(getString(R.string.ok))
        validationDialog.hasNegativeButton(false)
        validationDialog.show(childFragmentManager, ConfirmationDialogFragment.TAG)
    }

    private fun openDatePickerDialog(date: Long) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = date

        val onDateSetListener: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                viewModel.updateDate(calendar.timeInMillis)
            }

        context?.let {
            DatePickerDialog(
                it,
                onDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun createBackConfirmationDialog() {
        val backConfirmationDialogPositiveListener: ConfirmationDialogFragment.PositiveListener =
            object : ConfirmationDialogFragment.PositiveListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    findNavController().navigateUp()
                }
            }

        val message: String = if (viewModel.isEditTransaction()) {
            getString(R.string.cancel_edit_transaction)
        } else {
            getString(R.string.cancel_add_transaction)
        }
        val backConfirmationDialog = ConfirmationDialogFragment()
        backConfirmationDialog.setMessage(message)
        backConfirmationDialog.setPositiveButton(getString(R.string.ok))
        backConfirmationDialog.setPositiveButtonListener(backConfirmationDialogPositiveListener)
        backConfirmationDialog.setNegativeButton(getString(R.string.cancel))
        backConfirmationDialog.hasNegativeButton(true)
        backConfirmationDialog.show(childFragmentManager, ConfirmationDialogFragment.TAG)
    }

    // navigate to SearchListFragment
    private fun openSearchListFragment(selectionListInputModel: SelectionListInputModel) {
        val action =
            AddTransactionFragmentDirections.actionAddTransactionFragmentToSelectionListFragment(
                selectionListInputModel
            )
        findNavController().navigate(action)
    }

}