package com.zybooks.thebanddatabase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
const val ARG_BAND_ID = "band_id"

class DetailFragment : Fragment() {

    private var band: Band? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var bandId = 1

        // Get the band ID from the fragment arguments
        arguments?.let { bandId = it.getInt(ARG_BAND_ID) }

        // Get the selected band
        band = BandRepository.getInstance(requireContext()).getBand(bandId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_detail, container, false)

        if (band != null) {
            val nameTextView = rootView.findViewById<TextView>(R.id.band_name)
            nameTextView.text = band!!.name
            val descriptionTextView = rootView.findViewById<TextView>(R.id.band_description)
            descriptionTextView.text = band!!.description
        }

        return rootView
    }
}