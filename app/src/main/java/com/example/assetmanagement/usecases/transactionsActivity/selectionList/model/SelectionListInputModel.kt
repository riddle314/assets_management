package com.example.assetmanagement.usecases.transactionsActivity.selectionList.model

import android.os.Parcel
import android.os.Parcelable
import com.example.assetmanagement.common.Utils

data class SelectionListInputModel(
    val requestBundleKey: String,
    val searchTypeModel: SearchTypeModel
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: Utils.EMPTY_STRING,
        SearchTypeModel.values()[parcel.readInt()]
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(requestBundleKey)
        parcel.writeInt(searchTypeModel.ordinal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SelectionListInputModel> {
        override fun createFromParcel(parcel: Parcel): SelectionListInputModel {
            return SelectionListInputModel(parcel)
        }

        override fun newArray(size: Int): Array<SelectionListInputModel?> {
            return arrayOfNulls(size)
        }
    }
}