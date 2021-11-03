package com.example.assetmanagement.usecases.transactions_activity.add_transaction

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.assetmanagement.databinding.FragmentAddTransactionBinding
import com.example.assetmanagement.usecases.transactions_activity.transaction_details.TransactionDetailsFragmentArgs
import com.example.assetmanagement.usecases.common.ConfirmationDialogFragment
import com.example.assetmanagement.usecases.common.model.Event
import dagger.hilt.android.AndroidEntryPoint


import android.widget.ArrayAdapter
import com.example.assetmanagement.R


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
                // do I need to provide the values through viewmodel?
                R.layout.spinner_item, viewModel.getAssetTypeListOfValues(it)
            )
        }

        binding.transactionTypeSpinner.adapter = context?.let {
            ArrayAdapter<String>(
                it,
                // do I need to provide the values through viewmodel?
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
}