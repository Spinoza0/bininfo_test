package com.spinoza.bininfotest.presentation.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spinoza.bininfotest.R

class BinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textViewHistoryItem: TextView = itemView.findViewById(R.id.textViewHistoryItem)
}