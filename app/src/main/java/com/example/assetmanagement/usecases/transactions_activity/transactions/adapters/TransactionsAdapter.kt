package com.example.assetmanagement.usecases.transactions_activity.transactions.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.assetmanagement.R
import com.example.assetmanagement.databinding.TransactionItemViewBinding
import com.example.assetmanagement.usecases.transactions_activity.transactions.TransactionsViewModel

class TransactionsAdapter(
    var viewModel: TransactionsViewModel
) : RecyclerView.Adapter<TransactionsAdapter.TransactionItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionItemViewHolder {
        val viewHolderBinding: TransactionItemViewBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.transaction_item_view,
                parent,
                false
            )
        return TransactionItemViewHolder(viewHolderBinding, viewHolderBinding.root)
    }

    override fun onBindViewHolder(viewHolder: TransactionItemViewHolder, position: Int) {
        viewHolder.bind(viewModel, position)
    }

    override fun getItemCount(): Int {
        return viewModel.getTransactionsListSize()
    }

    inner class TransactionItemViewHolder(
        private val viewHolderBinding: TransactionItemViewBinding,
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            viewModel: TransactionsViewModel,
            position: Int
        ) {
            viewHolderBinding.viewModel = viewModel
            viewHolderBinding.position = position
        }
    }
}