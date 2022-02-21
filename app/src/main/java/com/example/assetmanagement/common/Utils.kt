package com.example.assetmanagement.common

import java.text.DateFormat
import java.util.*

class Utils {

    companion object {

        const val SPACE = " "
        const val EMPTY_STRING = ""
        const val ZERO_NUM = 0
        const val ZERO_DOUBLE = 0.0

        fun getFormattedPrice(price: Double, currency: String) =
            price.toString() + SPACE + currency

        @JvmStatic
        fun getDateToString(milliseconds: Long): String {
            return DateFormat.getDateInstance().format(Date(milliseconds))
        }

        fun getStringToDouble(source: String): Double {
            return try {
                source.toDouble()
            } catch (exception: NumberFormatException) {
                ZERO_DOUBLE
            }
        }
    }
}