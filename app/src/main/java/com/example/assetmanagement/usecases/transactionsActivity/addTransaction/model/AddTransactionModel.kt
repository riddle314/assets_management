package com.example.assetmanagement.usecases.transactionsActivity.addTransaction.model

import com.example.assetmanagement.common.model.AssetTypeModel
import com.example.assetmanagement.common.model.TransactionTypeModel

class AddTransactionModel(var transactionId: Int) {
    var assetModel: AssetModel = AssetModel("", AssetTypeModel.CURRENCY)
    var quantity: String = ""
    var price: String = ""
    var priceCurrency: String = ""
    var date: Long = 0
    var assetType: AssetTypeModel = AssetTypeModel.CURRENCY
    var transactionType: TransactionTypeModel = TransactionTypeModel.BUY

    constructor(
        transactionId: Int,
        assetModel: AssetModel,
        quantity: String,
        price: String,
        priceCurrency: String,
        date: Long,
        assetType: AssetTypeModel,
        transactionType: TransactionTypeModel
    ) : this(transactionId) {
        this.assetModel = assetModel
        this.quantity = quantity
        this.price = price
        this.priceCurrency = priceCurrency
        this.date = date
        this.assetType = assetType
        this.transactionType = transactionType
    }
}