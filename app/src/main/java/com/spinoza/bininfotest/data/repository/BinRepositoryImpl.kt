package com.spinoza.bininfotest.data.repository

import com.spinoza.bininfotest.data.network.BinApiFactory
import com.spinoza.bininfotest.domain.model.BinInfo
import com.spinoza.bininfotest.domain.repository.BinRepository

class BinRepositoryImpl : BinRepository {
    private val apiService = BinApiFactory.apiService

    override suspend fun getBinInfo(bin: String): BinInfo = apiService.getBinInfo(bin)
}