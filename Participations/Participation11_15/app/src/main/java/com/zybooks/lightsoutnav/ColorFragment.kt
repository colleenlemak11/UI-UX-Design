package com.zybooks.lightsoutnav

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment

class ColorFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_color, container, false)

        // Extract color ID from SharedPreferences
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val colorId = sharedPref.getInt("color", R.color.yellow)

        // Select the radio button matching the color ID
        val radioId = when (colorId) {
            R.color.red -> R.id.radio_red
            R.color.orange -> R.id.radio_orange
            R.color.green -> R.id.radio_green
            else -> R.id.radio_yellow
        }

        val radioButton = rootView.findViewById<RadioButton>(radioId)
        radioButton.isChecked = true

        // Add click callback to all radio buttons
        val colorRadioGroup = rootView.findViewById<RadioGroup>(R.id.color_radio_group)
        for (radioBtn in colorRadioGroup.children) {
            radioBtn.setOnClickListener(this::onColorSelected)
        }

        return rootView
    }

    private fun onColorSelected(view: View) {
        val colorId = when (view.id) {
            R.id.radio_red -> R.color.red
            R.id.radio_orange -> R.color.orange
            R.id.radio_green -> R.color.green
            else -> R.color.yellow
        }

        // Save selected color ID in SharedPreferences
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("color", colorId)
        editor.apply()
    }
}