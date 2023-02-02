package com.spinoza.bininfotest.domain.repository

import com.spinoza.bininfotest.domain.model.BinInfo

interface BinRepository {
    suspend fun getBinInfo(bin: String): BinInfo
}