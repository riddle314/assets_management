package com.example.repo.database.mechanism.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A data class for the Transactions Table
 */
@Entity(tableName = "transactions_table")
data class TransactionDetailsEntity(
    @ColumnInfo(name = "assets_name") val assetsName: String,
    @ColumnInfo(name = "quantity") val quantity: Double,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "priceCurrency") val priceCurrency: String,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "assetType") val assetType: Int,
    @ColumnInfo(name = "transactionType") val transactionType: Int
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    var transactionId: Int = 0

    constructor(
        transactionId: Int,
        assetsName: String,
        quantity: Double,
        price: Double,
        priceCurrency: String,
        date: Long,
        assetType: Int,
        transactionType: Int
    ) : this(assetsName, quantity, price, priceCurrency, date, assetType, transactionType) {
        this.transactionId = transactionId
    }
}
