package com.example.assetmanagement.usecases.transactionsActivity.transactionDetails.transformers

import com.example.assetmanagement.domain.model.TransactionDetailsResponseDomainModel
import com.example.assetmanagement.common.transformers.DataTransformers
import com.example.assetmanagement.usecases.transactionsActivity.transactionDetails.model.TransactionDetailsModel
import com.example.assetmanagement.common.Utils

class TransactionsDetailsDataTransformer {

    companion object {

        fun transform(transactionDetailsResponseDomainModel: TransactionDetailsResponseDomainModel) =
            TransactionDetailsModel(
                transactionDetailsResponseDomainModel.transactionId,
                transactionDetailsResponseDomainModel.assetsName,
                transactionDetailsResponseDomainModel.quantity.toString(),
                Utils.getFormattedPrice(
                    transactionDetailsResponseDomainModel.price,
                    transactionDetailsResponseDomainModel.priceCurrency
                ),
                Utils.getDateToString(transactionDetailsResponseDomainModel.date),
                DataTransformers.transformAssetType(transactionDetailsResponseDomainModel.assetType),
                DataTransformers.transformTransactionType(transactionDetailsResponseDomainModel.transactionType)
            )

    }

}