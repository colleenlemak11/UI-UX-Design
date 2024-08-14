package com.cpsc333fa23.memory

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class WinConditionDialogFragment: DialogFragment() {

    interface PlayAgainListener {
        fun onPlayAgainClick() {}
    }

    private lateinit var listener:PlayAgainListener

    override fun onCreateDialog(savedInstanceState: Bundle?)
            : Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("R.string.warning")
        builder.setMessage("R.string.warning_message")
        builder.setPositiveButton("Play Again?", listener.onPlayAgainClick())
        builder.setNegativeButton("No, thank you", null)

        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as PlayAgainListener
    }

}