package com.spinoza.bininfotest.data.repository

import com.spinoza.bininfotest.data.mapper.BinMapper
import com.spinoza.bininfotest.data.network.BinApiFactory
import com.spinoza.bininfotest.domain.model.BinInfo
import com.spinoza.bininfotest.domain.repository.BinRepository
import javax.inject.Inject

class BinRepositoryImpl @Inject constructor(
    private val mapper: BinMapper,
) : BinRepository {
    private val apiService = BinApiFactory.apiService
    override suspend fun getBinInfo(bin: String): BinInfo =
        mapper.mapDtoToEntity(apiService.getBinInfo(bin))
}