package com.example.assetmanagement.usecases.transactionsActivity.selectionList.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectionListInputModel(
    val requestBundleKey: String,
    val searchTypeModel: SearchTypeModel
) : Parcelable