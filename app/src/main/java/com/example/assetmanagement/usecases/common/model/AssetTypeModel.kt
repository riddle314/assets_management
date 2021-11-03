package com.example.assetmanagement.usecases.common.model

import android.content.Context
import com.example.assetmanagement.R
import androidx.databinding.InverseMethod




enum class AssetTypeModel {
    CURRENCY, STOCK, CRYPTO;

    // get the name of the type
    fun getName(context: Context): String = when (this) {
        CURRENCY -> context.getString(R.string.currency)
        STOCK -> context.getString(R.string.stock)
        CRYPTO -> context.getString(R.string.crypto)
    }

    companion object {

        fun listOfStringValues(context: Context): ArrayList<String> {
            val listOfNames = arrayListOf<String>()
            for (x in values()) {
                listOfNames.add(x.getName(context))
            }
            return listOfNames
        }

        @InverseMethod("toAssetTypeModel")
        @JvmStatic
        fun toPosition(assetTypeModel: AssetTypeModel?): Int {
            return assetTypeModel?.ordinal ?: 0
        }

        @JvmStatic
        fun toAssetTypeModel(position: Int): AssetTypeModel {
            return AssetTypeModel.values()[position]
        }
    }

}