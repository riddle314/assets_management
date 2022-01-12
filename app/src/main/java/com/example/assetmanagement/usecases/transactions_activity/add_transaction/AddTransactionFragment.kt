package com.example.assetmanagement.usecases.transactions_activity.add_transaction


import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
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
import com.example.assetmanagement.usecases.common.ConfirmationDialogFragment
import com.example.assetmanagement.usecases.common.model.Event
import com.example.assetmanagement.usecases.transactions_activity.transaction_details.TransactionDetailsFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


/**
 * An fragment to add a transaction
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

        binding.assetTypeSpinner.adapter = context?.let {
            ArrayAdapter<String>(
                it,
                R.layout.spinner_item, viewModel.getAssetTypeListOfValues(it)
            )
        }

        binding.transactionTypeSpinner.adapter = context?.let {
            ArrayAdapter<String>(
                it,
                R.layout.spinner_item, viewModel.getTransactionTypeListOfValues(it)
            )
        }

        viewModel.loadData()
        setupListenersAndObservers()
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
                it.getDataAndHandleEvent()
                openDatePickerDialog(it.getDataAndHandleEvent())
            }
        }
        viewModel.openDatePickerDialog.observe(viewLifecycleOwner, openDatePickerDialogObserver)

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

}