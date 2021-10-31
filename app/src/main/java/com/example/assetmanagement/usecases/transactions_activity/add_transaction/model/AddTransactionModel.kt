package com.example.assetmanagement.usecases.transactions_activity.add_transaction.model

class AddTransactionModel(var transactionId: Int) {
    var assetsName: String = ""
    var quantity: String = ""
    var price: String = ""
    var priceCurrency: String = ""
    var date: String = ""
    var assetType: String = ""
    var transactionType: String = ""

    constructor(
        transactionId: Int,
        assetsName: String,
        quantity: String,
        price: String,
        priceCurrency: String,
        date: String,
        assetType: String,
        transactionType: String
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