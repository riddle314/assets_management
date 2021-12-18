package com.example.assetmanagement.usecases.transactions_activity.add_transaction.model

import com.example.assetmanagement.usecases.common.model.AssetTypeModel
import com.example.assetmanagement.usecases.common.model.TransactionTypeModel

class AddTransactionModel(var transactionId: Int) {
    var assetsName: String = ""
    var quantity: String = ""
    var price: String = ""
    var priceCurrency: String = ""
    var date: Long = 0
    var assetType: AssetTypeModel = AssetTypeModel.CURRENCY
    var transactionType: TransactionTypeModel = TransactionTypeModel.BUY

    constructor(
        transactionId: Int,
        assetsName: String,
        quantity: String,
        price: String,
        priceCurrency: String,
        date: Long,
        assetType: AssetTypeModel,
        transactionType: TransactionTypeModel
    ) : this(transactionId) {
        this.assetsName = assetsName
        this.quantity = quantity
        this.price = price
        this.priceCurrency = priceCurrency
        this.date = date
        this.assetType = assetType
        this.transactionType = transactionType
    }
}