package com.example.assetmanagement.utils

class Utils {

    companion object {

        const val SPACE = " "
        const val EMPTY_STRING = ""
        const val ZERO_NUM = 0

        public fun getFormattedPrice(price: Double, currency: String) =
            price.toString() + SPACE + currency
    }
}