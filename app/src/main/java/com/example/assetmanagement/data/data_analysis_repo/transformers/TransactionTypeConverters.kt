package com.example.assetmanagement.data.data_analysis_repo.transformers

import com.example.assetmanagement.data.data_analysis_repo.model.TransactionTypeData

class TransactionTypeConverters {

    companion object {

        private const val BUY = 1
        private const val SELL = 2
        private const val DIVIDEND = 3

        fun getTransactionType(transactionType: Int): TransactionTypeData {
            return when (transactionType) {
                BUY -> TransactionTypeData.BUY
                SELL -> TransactionTypeData.SELL
                else -> TransactionTypeData.DIVIDEND
            }
        }


        fun convertTransactionType(transactionType: TransactionTypeData): Int {
            return when (transactionType) {
                TransactionTypeData.BUY -> BUY
                TransactionTypeData.SELL -> SELL
                TransactionTypeData.DIVIDEND -> DIVIDEND
            }
        }

    }
}