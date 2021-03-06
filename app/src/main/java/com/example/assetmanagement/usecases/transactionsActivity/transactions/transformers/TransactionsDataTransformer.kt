package com.example.assetmanagement.usecases.transactionsActivity.transactions.transformers

import com.example.domain.model.TransactionItemResponseDomainModel
import com.example.assetmanagement.common.transformers.DataTransformers
import com.example.assetmanagement.usecases.transactionsActivity.transactions.model.TransactionItemModel
import com.example.assetmanagement.common.Utils

class TransactionsDataTransformer {

    companion object {

        fun transform(transactionItemResponseDomainList: List<TransactionItemResponseDomainModel>): ArrayList<TransactionItemModel> {
            val transactionItemUIModelList = ArrayList<TransactionItemModel>(0)
            for (transactionItemDomain in transactionItemResponseDomainList) {
                transactionItemUIModelList.add(transform(transactionItemDomain))
            }
            return transactionItemUIModelList
        }

        fun transform(transactionItemResponseDomainModel: TransactionItemResponseDomainModel) =
            TransactionItemModel(
                transactionItemResponseDomainModel.transactionId,
                transactionItemResponseDomainModel.assetsName,
                transactionItemResponseDomainModel.quantity.toString(),
                Utils.getFormattedPrice(
                    transactionItemResponseDomainModel.price,
                    transactionItemResponseDomainModel.priceCurrency
                ),
                Utils.getDateToString(transactionItemResponseDomainModel.date),
                DataTransformers.transformAssetType(transactionItemResponseDomainModel.assetType),
                DataTransformers.transformTransactionType(transactionItemResponseDomainModel.transactionType)
            )
    }

}