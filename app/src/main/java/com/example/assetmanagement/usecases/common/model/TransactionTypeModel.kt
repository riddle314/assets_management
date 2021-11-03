package com.example.assetmanagement.usecases.common.model

import android.content.Context
import androidx.databinding.InverseMethod
import com.example.assetmanagement.R

enum class TransactionTypeModel {

    BUY, SELL, DIVIDEND;

    // get the name of the type
    fun getName(context: Context): String = when (this) {
        BUY -> context.getString(R.string.buy)
        SELL -> context.getString(R.string.sell)
        DIVIDEND -> context.getString(R.string.dividend)
    }

    companion object {

        fun listOfStringValues(context: Context): ArrayList<String> {
            val listOfNames = arrayListOf<String>()
            for (x in TransactionTypeModel.values()) {
                listOfNames.add(x.getName(context))
            }
            return listOfNames
        }

        @InverseMethod("toTransactionTypeModel")
        @JvmStatic
        fun toPosition(transactionTypeModel:  TransactionTypeModel?): Int {
            return transactionTypeModel?.ordinal ?: 0
        }

        @JvmStatic
        fun toTransactionTypeModel(position: Int): TransactionTypeModel {
            return TransactionTypeModel.values()[position]
        }
    }
}