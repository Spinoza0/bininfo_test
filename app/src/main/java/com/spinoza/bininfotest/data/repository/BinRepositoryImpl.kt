package com.spinoza.bininfotest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.spinoza.bininfotest.data.database.HistoryDao
import com.spinoza.bininfotest.data.mapper.BinMapper
import com.spinoza.bininfotest.data.network.ApiService
import com.spinoza.bininfotest.domain.model.Bin
import com.spinoza.bininfotest.domain.repository.BinRepository
import com.spinoza.bininfotest.domain.repository.BinState
import javax.inject.Inject

class BinRepositoryImpl @Inject constructor(
    private val mapper: BinMapper,
    private val historyDao: HistoryDao,
    private val apiService: ApiService,
) : BinRepository {

    private val state = MutableLiveData<BinState>()
    override fun getState(): LiveData<BinState> = state

    override suspend fun loadBinInfo(binValue: String) {
        if (state.value != BinState.Loading) {
            state.value = BinState.Loading
            runCatching {
                val response = apiService.getBinInfo(binValue)
                if (response.isSuccessful) {
                    response.body()?.let {
                        state.value = BinState.BinInfoData(mapper.mapDtoToEntity(it))
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: ""
                    state.value = BinState.Error("${response.code()} $errorBody")
                }
            }.onFailure {
                state.value = BinState.Error(getErrorText(it))
            }
        }
    }

    override suspend fun getBinsHistory() {
        runCatching {
            state.value = BinState.BinsHistory(mapper.mapDbModelToEntity(historyDao.getHistory()))
        }.onFailure {
            state.value = BinState.Error(getErrorText(it))
        }
    }

    override suspend fun insertBinToHistory(bin: Bin) {
        runCatching {
            historyDao.insertToHistory(mapper.mapEntityToDbModel(bin))

        }.onSuccess {
            getBinsHistory()
        }.onFailure {
            state.value = BinState.Error(getErrorText(it))
        }
    }

    override suspend fun removeBinFromHistory(bin: Bin) {
        runCatching {
            historyDao.removeFromHistory(bin.value)
        }.onSuccess {
            getBinsHistory()
        }.onFailure {
            state.value = BinState.Error(getErrorText(it))
        }
    }

    override suspend fun clearBinsHistory() {
        runCatching {
            historyDao.clearHistory()
        }.onSuccess {
            getBinsHistory()
        }.onFailure {
            state.value = BinState.Error(getErrorText(it))
        }
    }

    private fun getErrorText(e: Throwable) = e.localizedMessage ?: e.message ?: e.toString()
}