package com.example.assetmanagement.usecases.transactionsActivity.selectionList.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectionListResultModel(
    val name: String,
    val description: String,
    val searchTypeModel: SearchTypeModel
) : Parcelable