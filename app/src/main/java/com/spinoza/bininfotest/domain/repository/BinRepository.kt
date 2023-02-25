package com.spinoza.bininfotest.domain.repository

import androidx.lifecycle.LiveData
import com.spinoza.bininfotest.domain.model.Bin

interface BinRepository {

    fun getState(): LiveData<State>

    suspend fun loadBinInfo(binValue: String)

    suspend fun getBinsHistory()

    suspend fun insertBinToHistory(bin: Bin)

    suspend fun removeBinFromHistory(bin: Bin)

    suspend fun clearBinsHistory()
}