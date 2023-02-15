package com.spinoza.bininfotest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val binInfo = MutableLiveData<BinInfo>()
    private val error = MutableLiveData<String>()
    private val isLoading = MutableLiveData(false)

    override fun getBinInfo(): LiveData<BinInfo> = binInfo
    override fun isError() = error
    override fun isLoading() = isLoading

    override suspend fun loadBinInfo(bin: String) {
        if (isLoading.value == false) {
            isLoading.value = true
            val response = apiService.getBinInfo(bin)
            isLoading.value = false
            if (response.isSuccessful) {
                response.body()?.let {
                    binInfo.value = mapper.mapDtoToEntity(it)
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: ""
                error.value = "${response.code()} $errorBody"
            }
        }
    }

    override fun getBinsHistory(): LiveData<List<Bin>> =
        Transformations.map(historyDao.getHistory()) { list ->
            list.map { mapper.mapDbModelToEntity(it) }
        }

    override suspend fun insertBinToHistory(bin: Bin) {
        historyDao.insertToHistory(mapper.mapEntityToDbModel(bin))
    }

    override suspend fun removeBinFromHistory(bin: Bin) {
        historyDao.removeFromHistory(bin.value)
    }

    override suspend fun clearBinsHistory() {
        historyDao.clearHistory()
    }
}