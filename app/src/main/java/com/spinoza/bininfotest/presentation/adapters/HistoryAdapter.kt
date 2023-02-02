package com.spinoza.bininfotest.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.spinoza.bininfotest.databinding.ItemHistoryBinding
import com.spinoza.bininfotest.domain.model.Bin

class HistoryAdapter : ListAdapter<Bin, BinViewHolder>(BinDiffCallback()) {

    var onBinClickListener: ((Bin) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BinViewHolder, position: Int) {
        with(getItem(position)) {
            holder.binding.textViewHistoryItem.text = this.value
            holder.binding.root.setOnClickListener { onBinClickListener?.invoke(this) }
        }
    }
}