package com.spinoza.bininfotest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.spinoza.bininfotest.data.database.HistoryDao
import com.spinoza.bininfotest.data.mapper.BinMapper
import com.spinoza.bininfotest.data.network.ApiService
import com.spinoza.bininfotest.domain.model.Bin
import com.spinoza.bininfotest.domain.model.BinInfo
import com.spinoza.bininfotest.domain.repository.BinRepository
import javax.inject.Inject

class BinRepositoryImpl @Inject constructor(
    private val mapper: BinMapper,
    private val historyDao: HistoryDao,
    private val apiService: ApiService,
) : BinRepository {

    override suspend fun getBinInfo(bin: String): BinInfo =
        mapper.mapDtoToEntity(apiService.getBinInfo(bin))

    override fun getHistory(): LiveData<List<Bin>> =
        Transformations.map(historyDao.getHistory()) { list ->
            list.map { mapper.mapDbModelToEntity(it) }
        }

    override suspend fun insertToHistory(bin: Bin) {
        historyDao.insertToHistory(mapper.mapEntityToDbModel(bin))
    }

    override suspend fun clearHistory() {
        historyDao.clearHistory()
    }
}