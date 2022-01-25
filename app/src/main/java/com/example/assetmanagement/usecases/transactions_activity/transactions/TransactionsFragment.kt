package com.example.assetmanagement.usecases.transactions_activity.transactions

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assetmanagement.R
import com.example.assetmanagement.databinding.FragmentTransactionsBinding
import com.example.assetmanagement.usecases.common.model.Event
import com.example.assetmanagement.usecases.transactions_activity.transactions.adapters.TransactionsAdapter
import com.example.assetmanagement.usecases.transactions_activity.transactions.model.TransactionItemModel
import com.example.assetmanagement.utils.Utils
import dagger.hilt.android.AndroidEntryPoint


/**
 * A fragment with the list of transactions
 */
@AndroidEntryPoint
class TransactionsFragment : Fragment(R.layout.fragment_transactions) {

    private lateinit var binding: FragmentTransactionsBinding
    private lateinit var adapter: TransactionsAdapter

    private val viewModel: TransactionsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
    }

    private fun setupUI(view: View) {
        // setup data binding
        binding = FragmentTransactionsBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // setup recycle view
        val listOfTransactionsRecyclerView = binding.listOfTransactions
        adapter = TransactionsAdapter(viewModel)
        listOfTransactionsRecyclerView.adapter = adapter
        listOfTransactionsRecyclerView.layoutManager = LinearLayoutManager(context)

        setupListenersAndObservers()

        // load data
        viewModel.firstTimeLoadData()
    }

    private fun setupListenersAndObservers() {
        // set up listeners

        val searchView = binding.searchView
        val onQueryTextListener: android.widget.SearchView.OnQueryTextListener =
            object : android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // fetch the data for specific query
                    viewModel.fetchTransactionsForSearchQuery(newText ?: Utils.EMPTY_STRING)
                    return false
                }
            }
        searchView.setOnQueryTextListener(onQueryTextListener)

        // set up observers
        val transactionsListObserver =
            Observer<List<TransactionItemModel>> { _ ->
                updateTransactionsList()
            }
        viewModel.transactionsList.observe(viewLifecycleOwner, transactionsListObserver)

        val navigateToAddTransactionObserver = Observer<Event<Boolean>> { event ->
            if (!event.isEventHandled) {
                event.handleEvent()
                navigateToAddTransactionScreen()
            }
        }
        viewModel.navigateToAddTransaction.observe(
            viewLifecycleOwner,
            navigateToAddTransactionObserver
        )

        val navigateToTransactionDetailsObserver = Observer<Event<Int>> { event ->
            if (!event.isEventHandled) {
                navigateToTransactionDetailsScreen(event.getDataAndHandleEvent())
            }
        }
        viewModel.navigateToTransactionDetails.observe(
            viewLifecycleOwner,
            navigateToTransactionDetailsObserver
        )

        // Handle the back button and up button event
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, true) {
            //empty callback for back press
        }

    }

    private fun updateTransactionsList() {
        adapter.notifyDataSetChanged()
    }

    private fun navigateToAddTransactionScreen() {
        val action =
            TransactionsFragmentDirections.actionTransactionsFragmentToAddTransactionFragment()
        findNavController().navigate(action)
    }

    private fun navigateToTransactionDetailsScreen(transactionId: Int) {
        val action =
            TransactionsFragmentDirections.actionTransactionsFragmentToTransactionDetailsFragment(
                transactionId
            )
        findNavController().navigate(action)
    }


}