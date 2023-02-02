package com.spinoza.bininfotest.domain

import androidx.lifecycle.LiveData

interface BinInfoDao {
    fun getHistory(): LiveData<List<Bin>>
    suspend fun insertToHistory(bin: Bin)
    suspend fun clearHistory()
}