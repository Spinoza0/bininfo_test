package com.spinoza.bininfotest.domain.repository

import androidx.lifecycle.LiveData
import com.spinoza.bininfotest.domain.model.Bin
import com.spinoza.bininfotest.domain.model.BinInfo

interface BinRepository {
    fun getBinInfo(): LiveData<BinInfo>
    suspend fun loadBinInfo(bin: String)
    fun getBinsHistory(): LiveData<List<Bin>>
    suspend fun insertBinToHistory(bin: Bin)
    suspend fun clearBinsHistory()
    fun isError(): LiveData<String>
    fun isLoading(): LiveData<Boolean>
}