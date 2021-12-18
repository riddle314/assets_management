package com.example.assetmanagement.usecases.transactions_activity.transactions.transformers

import com.example.assetmanagement.domain.model.TransactionItemResponseDomainModel
import com.example.assetmanagement.usecases.common.transformers.DataTransformers
import com.example.assetmanagement.usecases.transactions_activity.transactions.model.TransactionItemModel
import com.example.assetmanagement.utils.Utils

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