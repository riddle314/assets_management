package com.example.assetmanagement.usecases.transactions_activity.transaction_details.transformers

import com.example.assetmanagement.domain.model.TransactionDetailsResponseDomainModel
import com.example.assetmanagement.usecases.common.transformers.DataTransformers
import com.example.assetmanagement.usecases.transactions_activity.transaction_details.model.TransactionDetailsModel
import com.example.assetmanagement.utils.Utils

class TransactionsDetailsDataTransformer {

    companion object {

        fun transform(transactionDetailsResponseDomainModel: TransactionDetailsResponseDomainModel) =
            TransactionDetailsModel(
                transactionDetailsResponseDomainModel.transactionId,
                transactionDetailsResponseDomainModel.assetsName,
                transactionDetailsResponseDomainModel.quantity,
                Utils.getFormattedPrice(
                    transactionDetailsResponseDomainModel.price,
                    transactionDetailsResponseDomainModel.priceCurrency
                ),
                transactionDetailsResponseDomainModel.date,
                DataTransformers.transformAssetType(transactionDetailsResponseDomainModel.assetType),
                DataTransformers.transformTransactionType(transactionDetailsResponseDomainModel.transactionType)
            )

    }

}