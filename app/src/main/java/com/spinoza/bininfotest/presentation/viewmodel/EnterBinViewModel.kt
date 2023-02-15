package com.spinoza.bininfotest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.bininfotest.domain.model.Bin
import com.spinoza.bininfotest.domain.repository.BinRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class EnterBinViewModel @Inject constructor(private val binRepository: BinRepository) :
    ViewModel() {

    val history = binRepository.getBinsHistory()
    val isError = binRepository.isError()

    fun insertToHistory(bin: Bin) {
        viewModelScope.launch {
            var needToInsert = true
            history.value?.let { needToInsert = !it.contains(bin) }
            if (needToInsert) {
                binRepository.insertBinToHistory(bin)
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch { binRepository.clearBinsHistory() }
    }
}