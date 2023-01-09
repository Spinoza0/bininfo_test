package com.spinoza.bininfotest.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.spinoza.bininfotest.domain.Bin

class BinDiffCallback : DiffUtil.ItemCallback<Bin>() {

    override fun areItemsTheSame(oldItem: Bin, newItem: Bin): Boolean {
        return oldItem.value == newItem.value
    }

    override fun areContentsTheSame(oldItem: Bin, newItem: Bin): Boolean {
        return oldItem.value == newItem.value
    }
}