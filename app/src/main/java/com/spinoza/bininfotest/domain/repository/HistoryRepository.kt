package com.spinoza.bininfotest.domain.repository

import androidx.lifecycle.LiveData
import com.spinoza.bininfotest.domain.model.Bin

interface HistoryRepository {
    fun getHistory(): LiveData<List<Bin>>
    suspend fun insertToHistory(bin: Bin)
    suspend fun clearHistory()
}