package com.example.assetmanagement.usecases.transactions_activity.add_transaction

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.assetmanagement.R
import com.example.assetmanagement.databinding.FragmentAddTransactionBinding
import com.example.assetmanagement.usecases.transactions_activity.transaction_details.TransactionDetailsFragmentArgs
import com.example.assetmanagement.utils.ConfirmationDialogFragment
import com.example.assetmanagement.utils.Event
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.prefillWithData()

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
        val deleteConfirmationDialog = ConfirmationDialogFragment()
        deleteConfirmationDialog.setMessage(getString(R.string.validation_dialog_message))
        deleteConfirmationDialog.setPositiveButton(getString(R.string.ok))
        deleteConfirmationDialog.hasNegativeButton(false)
        deleteConfirmationDialog.show(childFragmentManager, ConfirmationDialogFragment.TAG)
    }
}