package com.spinoza.bininfotest.domain.repository

import androidx.lifecycle.LiveData
import com.spinoza.bininfotest.domain.model.Bin
import com.spinoza.bininfotest.domain.model.BinInfo

interface BinRepository {
    suspend fun getBinInfo(bin: String): BinInfo
    fun getHistory(): LiveData<List<Bin>>
    suspend fun insertToHistory(bin: Bin)
    suspend fun clearHistory()
}