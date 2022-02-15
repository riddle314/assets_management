package com.example.assetmanagement.usecases.transactionsActivity.selectionList.model

import android.os.Parcel
import android.os.Parcelable
import com.example.assetmanagement.utils.Utils

data class SelectionListResultModel(
    val name: String,
    val description: String,
    val searchTypeModel: SearchTypeModel
) : Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readString() ?: Utils.EMPTY_STRING,
        parcel.readString() ?: Utils.EMPTY_STRING,
        SearchTypeModel.values()[parcel.readInt()]
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(searchTypeModel.ordinal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SelectionListResultModel> {
        override fun createFromParcel(parcel: Parcel): SelectionListResultModel {
            return SelectionListResultModel(parcel)
        }

        override fun newArray(size: Int): Array<SelectionListResultModel?> {
            return arrayOfNulls(size)
        }
    }
}