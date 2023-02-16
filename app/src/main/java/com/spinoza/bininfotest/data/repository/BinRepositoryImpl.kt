package com.spinoza.bininfotest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.spinoza.bininfotest.data.database.HistoryDao
import com.spinoza.bininfotest.data.mapper.BinMapper
import com.spinoza.bininfotest.data.network.ApiService
import com.spinoza.bininfotest.domain.model.Bin
import com.spinoza.bininfotest.domain.repository.BinRepository
import com.spinoza.bininfotest.domain.repository.State
import javax.inject.Inject

class BinRepositoryImpl @Inject constructor(
    private val mapper: BinMapper,
    private val historyDao: HistoryDao,
    private val apiService: ApiService,
) : BinRepository {

    private val state = MutableLiveData<State>()
    override fun getState(): LiveData<State> = state

    override suspend fun loadBinInfo(binValue: String) {
        if (state.value != State.Loading) {
            state.value = State.Loading
            val response = apiService.getBinInfo(binValue)
            if (response.isSuccessful) {
                response.body()?.let {
                    state.value = State.BinInfoData(mapper.mapDtoToEntity(it))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: ""
                state.value = State.Error("${response.code()} $errorBody")
            }
        }
    }

    override suspend fun getBinsHistory() {
        runCatching {
            state.value = State.BinsHistory(mapper.mapDbModelToEntity(historyDao.getHistory()))
        }.onFailure {
            state.value = State.Error(getErrorText(it))
        }
    }

    override suspend fun insertBinToHistory(bin: Bin) {
        kotlin.runCatching {
            historyDao.insertToHistory(mapper.mapEntityToDbModel(bin))

        }.onSuccess {
            getBinsHistory()
        }.onFailure {
            state.value = State.Error(getErrorText(it))
        }
    }

    override suspend fun removeBinFromHistory(bin: Bin) {
        kotlin.runCatching {
            historyDao.removeFromHistory(bin.value)
        }.onSuccess {
            getBinsHistory()
        }.onFailure {
            state.value = State.Error(getErrorText(it))
        }
    }

    override suspend fun clearBinsHistory() {
        kotlin.runCatching {
            historyDao.clearHistory()
        }.onSuccess {
            getBinsHistory()
        }.onFailure {
            state.value = State.Error(getErrorText(it))
        }
    }

    private fun getErrorText(e: Throwable) = e.localizedMessage ?: e.message ?: e.toString()
}