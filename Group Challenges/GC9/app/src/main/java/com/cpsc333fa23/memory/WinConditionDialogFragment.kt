package com.cpsc333fa23.memory

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.cpsc333fa23.gc7.R

class WinConditionDialogFragment : DialogFragment() {

    interface OnWinConditionSelectedListener {
        fun onWinConditionClick(choice: Boolean)
    }

    private lateinit var listener: OnWinConditionSelectedListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(R.string.win_condition_title)
        builder.setMessage(R.string.win_condition_message)
        builder.setPositiveButton(R.string.play_again_confirm) { _,
                                                                 _ ->
            listener.onWinConditionClick(true)
        }
        builder.setNegativeButton(R.string.no_thanks){ _,
                                                       _ ->
            listener.onWinConditionClick(false)
        }
        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnWinConditionSelectedListener
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listener.onWinConditionClick(false)
    }
}