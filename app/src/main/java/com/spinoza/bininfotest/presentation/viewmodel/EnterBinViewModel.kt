package com.spinoza.bininfotest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.bininfotest.domain.model.Bin
import com.spinoza.bininfotest.domain.repository.HistoryRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class EnterBinViewModel @Inject constructor(private val historyRepository: HistoryRepository) :
    ViewModel() {

    val history = historyRepository.getHistory()

    private val _isError: MutableLiveData<String> = MutableLiveData()
    val isError: LiveData<String>
        get() = _isError

    fun insertToHistory(bin: Bin) {
        viewModelScope.launch {
            var needToInsert = true
            history.value?.let { needToInsert = !it.contains(bin) }
            if (needToInsert) {
                historyRepository.insertToHistory(bin)
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch { historyRepository.clearHistory() }
    }
}