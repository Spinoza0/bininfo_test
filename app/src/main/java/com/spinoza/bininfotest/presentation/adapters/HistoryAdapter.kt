package com.spinoza.bininfotest.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.spinoza.bininfotest.R
import com.spinoza.bininfotest.domain.Bin

class HistoryAdapter : ListAdapter<Bin, BinViewHolder>(BinDiffCallback()) {

    var onBinClickListener: ((Bin) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return BinViewHolder(view)
    }

    override fun onBindViewHolder(holder: BinViewHolder, position: Int) {
        with(getItem(position)) {
            holder.textViewHistoryItem.text = this.value
            holder.itemView.setOnClickListener { onBinClickListener?.invoke(this) }
        }
    }
}