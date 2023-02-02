package com.spinoza.bininfotest.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.spinoza.bininfotest.data.database.DataBase
import com.spinoza.bininfotest.data.mapper.BinMapper
import com.spinoza.bininfotest.domain.model.Bin
import com.spinoza.bininfotest.domain.repository.HistoryRepository

class HistoryRepositoryImpl(application: Application) : HistoryRepository {
    private val historyDao = DataBase.getInstance(application).historyDao()
    private val mapper = BinMapper()

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