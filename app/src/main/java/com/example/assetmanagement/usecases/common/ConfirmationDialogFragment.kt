package com.example.assetmanagement.usecases.common

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.assetmanagement.utils.Utils

class ConfirmationDialogFragment : DialogFragment() {

    private var mMessage: String = Utils.EMPTY_STRING
    private var mPositiveButtonMessage: String = Utils.EMPTY_STRING
    private var mNegativeButtonMessage: String = Utils.EMPTY_STRING
    private var isNegativeButtonEnabled: Boolean = false

    interface PositiveListener : DialogInterface.OnClickListener
    interface NegativeListener : DialogInterface.OnClickListener

    var mPositiveButtonListener: PositiveListener? = null
    var mNegativeButtonListener: NegativeListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (isNegativeButtonEnabled) {
            return AlertDialog.Builder(requireContext())
                .setMessage(mMessage)
                .setPositiveButton(mPositiveButtonMessage, mPositiveButtonListener)
                .setNegativeButton(mNegativeButtonMessage, mNegativeButtonListener)
                .create()
        } else {
            return AlertDialog.Builder(requireContext())
                .setMessage(mMessage)
                .setPositiveButton(mPositiveButtonMessage, mPositiveButtonListener)
                .create()
        }
    }

    fun setMessage(message: String) {
        mMessage = message
    }

    fun setPositiveButton(positiveButtonMessage: String) {
        mPositiveButtonMessage = positiveButtonMessage
    }

    fun setNegativeButton(negativeButtonMessage: String) {
        mNegativeButtonMessage = negativeButtonMessage
    }

    fun setPositiveButtonListener(positiveButtonListener: PositiveListener) {
        mPositiveButtonListener = positiveButtonListener
    }

    fun setNegativeButtonListener(negativeButtonListener: NegativeListener) {
        mNegativeButtonListener = negativeButtonListener
    }

    fun hasNegativeButton(hasNegativeButton: Boolean) {
        isNegativeButtonEnabled = hasNegativeButton
    }

    companion object {
        const val TAG = "ConfirmationDialog"
    }


}