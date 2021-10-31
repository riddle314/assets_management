package com.example.assetmanagement.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

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

    public fun setMessage(message: String) {
        mMessage = message
    }

    public fun setPositiveButton(positiveButtonMessage: String) {
        mPositiveButtonMessage = positiveButtonMessage
    }

    public fun setNegativeButton(negativeButtonMessage: String) {
        mNegativeButtonMessage = negativeButtonMessage
    }

    public fun setPositiveButtonListener(positiveButtonListener: PositiveListener) {
        mPositiveButtonListener = positiveButtonListener
    }

    public fun setNegativeButtonListener(negativeButtonListener: NegativeListener) {
        mNegativeButtonListener = negativeButtonListener
    }

    public fun hasNegativeButton(hasNegativeButton: Boolean) {
        isNegativeButtonEnabled = hasNegativeButton
    }

    companion object {
        const val TAG = "ConfirmationDialog"
    }


}