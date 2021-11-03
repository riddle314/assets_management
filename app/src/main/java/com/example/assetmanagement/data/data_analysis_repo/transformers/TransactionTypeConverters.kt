package com.example.assetmanagement.data.data_analysis_repo.transformers

import com.example.assetmanagement.domain.model.AssetTypeDomain
import com.example.assetmanagement.domain.model.TransactionTypeDomain

class TransactionTypeConverters {

    companion object {

        private const val BUY = 1
        private const val SELL = 2
        private const val DIVIDEND = 3

        fun getTransactionType(transactionType: Int): TransactionTypeDomain {
            return when (transactionType) {
                BUY -> TransactionTypeDomain.BUY
                SELL -> TransactionTypeDomain.SELL
                else -> TransactionTypeDomain.DIVIDEND
            }
        }


        fun convertTransactionType(transactionType: TransactionTypeDomain): Int {
            return when (transactionType) {
                TransactionTypeDomain.BUY -> BUY
                TransactionTypeDomain.SELL -> SELL
                TransactionTypeDomain.DIVIDEND -> DIVIDEND
            }
        }

    }
}