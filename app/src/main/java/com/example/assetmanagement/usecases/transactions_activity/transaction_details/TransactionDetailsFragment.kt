package com.example.assetmanagement.usecases.transactions_activity.transaction_details

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.assetmanagement.R
import com.example.assetmanagement.databinding.TransactionDetailsFragmentBinding
import com.example.assetmanagement.usecases.common.ConfirmationDialogFragment
import com.example.assetmanagement.usecases.common.model.Event
import dagger.hilt.android.AndroidEntryPoint

/**
 * An fragment to see the details of a transaction
 */
@AndroidEntryPoint
class TransactionDetailsFragment : Fragment(R.layout.transaction_details_fragment) {

    private val viewModel: TransactionDetailsViewModel by viewModels()
    private lateinit var binding: TransactionDetailsFragmentBinding

    private val args: TransactionDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
    }

    // setup menu actions for action bar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.transaction_details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                navigateToTransactionEditScreen(viewModel.transactionId)
                true
            }
            R.id.action_delete -> {
                createDeleteConfirmationDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupUI(view: View) {
        // setup view model
        viewModel.transactionId = args.transactionId
        // setup data binding
        binding = TransactionDetailsFragmentBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // enable menu on action bar
        setHasOptionsMenu(true)

        setupListenersAndObservers()

        // load data
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
    }

    private fun createDeleteConfirmationDialog() {
        val deleteConfirmationDialogPositiveListener: ConfirmationDialogFragment.PositiveListener =
            object : ConfirmationDialogFragment.PositiveListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    viewModel.deleteTransaction()
                }
            }

        val deleteConfirmationDialog = ConfirmationDialogFragment()
        deleteConfirmationDialog.setMessage(getString(R.string.delete_confirmation))
        deleteConfirmationDialog.setPositiveButton(getString(R.string.ok))
        deleteConfirmationDialog.setPositiveButtonListener(deleteConfirmationDialogPositiveListener)
        deleteConfirmationDialog.setNegativeButton(getString(R.string.cancel))
        deleteConfirmationDialog.hasNegativeButton(true)
        deleteConfirmationDialog.show(childFragmentManager, ConfirmationDialogFragment.TAG)
    }

    private fun navigateToTransactionEditScreen(transactionId: Int) {
        val action =
            TransactionDetailsFragmentDirections.actionTransactionDetailsFragmentToAddTransactionFragment(
                transactionId
            )
        findNavController().navigate(action)
    }

    private fun navigateToTransactionsScreen() {
        val action =
            TransactionDetailsFragmentDirections.actionTransactionDetailsFragmentToTransactionsFragment()
        findNavController().navigate(action)
    }

}