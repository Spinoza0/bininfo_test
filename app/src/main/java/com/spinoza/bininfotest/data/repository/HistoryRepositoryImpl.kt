package com.spinoza.bininfotest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.spinoza.bininfotest.data.database.HistoryDao
import com.spinoza.bininfotest.data.mapper.BinMapper
import com.spinoza.bininfotest.domain.model.Bin
import com.spinoza.bininfotest.domain.repository.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao,
    private val mapper: BinMapper,
) : HistoryRepository {

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