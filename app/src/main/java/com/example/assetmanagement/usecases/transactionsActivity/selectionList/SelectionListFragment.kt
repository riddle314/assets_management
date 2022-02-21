package com.example.assetmanagement.usecases.transactionsActivity.selectionList

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assetmanagement.R
import com.example.assetmanagement.databinding.FragmentSelectionListBinding
import com.example.assetmanagement.common.model.Event
import com.example.assetmanagement.usecases.transactionsActivity.selectionList.adapters.SelectionListAdapter
import com.example.assetmanagement.usecases.transactionsActivity.selectionList.model.SelectionListResultModel
import com.example.assetmanagement.common.Utils
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment to select and search from a list. The type of the items in the list are configurable.
 */
@AndroidEntryPoint
class SelectionListFragment : Fragment(R.layout.fragment_selection_list) {

    companion object {
        const val RESULT_REQUEST_KEY = "RESULT_SELECTION_LIST_FRAGMENT_REQUEST_KEY"
    }

    private lateinit var binding: FragmentSelectionListBinding
    private lateinit var adapter: SelectionListAdapter
    private lateinit var resultBundleKey: String

    private val viewModel: SelectionListViewModel by viewModels()

    private val args: SelectionListFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
    }

    private fun setupUI(view: View) {
        // setup view model
        viewModel.searchType = args.selectionListInputModel.searchTypeModel
        resultBundleKey = args.selectionListInputModel.requestBundleKey

        // setup data binding
        binding = FragmentSelectionListBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        context?.let {
            // setup title
            if (activity is AppCompatActivity) {
                val appCompatActivity = activity as AppCompatActivity
                appCompatActivity.supportActionBar?.title =
                    getString(R.string.select) + Utils.SPACE + viewModel.searchType.getName(it)
            }

            // setup search hint
            binding.searchView.queryHint =
                getString(R.string.search_hint) + Utils.SPACE + viewModel.searchType.getName(it)
        }

        // setup recycle view
        val recyclerView = binding.recycleView
        adapter = SelectionListAdapter(viewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

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
                    viewModel.fetchListItemsForSearchQuery(newText ?: Utils.EMPTY_STRING)
                    return false
                }
            }
        searchView.setOnQueryTextListener(onQueryTextListener)

        // set up observers
        val searchItemsListObserver =
            Observer<List<SelectionListResultModel>> { _ ->
                updateItemsList()
            }
        viewModel.itemsList.observe(viewLifecycleOwner, searchItemsListObserver)

        // send the SearchItemModel result
        val returnSelectionEventObserver = Observer<Event<SelectionListResultModel>> { event ->
            if (!event.isEventHandled) {
                val result = Bundle()
                result.putParcelable(resultBundleKey, event.getDataAndHandleEvent())
                parentFragmentManager.setFragmentResult(RESULT_REQUEST_KEY, result)
                (activity as AppCompatActivity).onBackPressed()
            }
        }
        viewModel.returnSelectionEvent.observe(
            viewLifecycleOwner,
            returnSelectionEventObserver
        )

    }

    private fun updateItemsList() {
        adapter.notifyDataSetChanged()
    }

}