package com.example.assetmanagement.usecases.transactionsActivity.selectionList.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.assetmanagement.R
import com.example.assetmanagement.databinding.SelectionListItemViewBinding
import com.example.assetmanagement.usecases.transactionsActivity.selectionList.SelectionListViewModel

class SelectionListAdapter(
    var viewModel: SelectionListViewModel
) : RecyclerView.Adapter<SelectionListAdapter.SelectionListItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionListItemViewHolder {
        val viewHolderBinding: SelectionListItemViewBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.selection_list_item_view,
                parent,
                false
            )
        return SelectionListItemViewHolder(viewHolderBinding, viewHolderBinding.root)
    }

    override fun onBindViewHolder(viewHolder: SelectionListItemViewHolder, position: Int) {
        viewHolder.bind(viewModel, position)
    }

    override fun getItemCount(): Int {
        return viewModel.getSearchItemsListSize()
    }

    inner class SelectionListItemViewHolder(
        private val viewHolderBinding: SelectionListItemViewBinding,
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            viewModel: SelectionListViewModel,
            position: Int
        ) {
            viewHolderBinding.viewModel = viewModel
            viewHolderBinding.position = position
        }
    }
}
