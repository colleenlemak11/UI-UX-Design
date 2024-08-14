package com.zybooks.thebanddatabase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_list, container, false)

        // Click listener for the RecyclerView
        val onClickListener = View.OnClickListener { itemView: View ->

            // Create fragment arguments containing the selected band ID
            val selectedBandId = itemView.tag as Int
            val args = Bundle()
            args.putInt(ARG_BAND_ID, selectedBandId)

            val detailFragContainer = rootView.findViewById<View>(R.id.detail_frag_container)
            if (detailFragContainer == null) {
                // Replace list with details
                Navigation.findNavController(itemView).navigate(R.id.show_item_detail, args)
            } else {
                // Show details on the right
                Navigation.findNavController(detailFragContainer).navigate(R.id.fragment_detail, args)
            }
        }

        // Send bands to RecyclerView
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.band_list)
        val bands = BandRepository.getInstance(requireContext()).bandList
        recyclerView.adapter = BandAdapter(bands, onClickListener)

        val divider = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(divider)

        return rootView
    }

    private class BandAdapter(private val bandList: List<Band>,
                              private val onClickListener: View.OnClickListener) :
        RecyclerView.Adapter<BandHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BandHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return BandHolder(layoutInflater, parent)
        }

        override fun onBindViewHolder(holder: BandHolder, position: Int) {
            val band = bandList[position]
            holder.bind(band)
            holder.itemView.tag = band.id
            holder.itemView.setOnClickListener(onClickListener)
        }

        override fun getItemCount(): Int {
            return bandList.size
        }
    }

    private class BandHolder(inflater: LayoutInflater, parent: ViewGroup?) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_band, parent, false)) {

        private val nameTextView: TextView

        init {
            nameTextView = itemView.findViewById(R.id.band_name)
        }

        fun bind(band: Band) {
            nameTextView.text = band.name
        }
    }
}