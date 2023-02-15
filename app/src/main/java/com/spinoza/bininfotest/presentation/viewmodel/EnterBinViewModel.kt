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
            binRepository.insertBinToHistory(bin)
        }
    }

    fun removeFromHistory(bin: Bin) {
        viewModelScope.launch {
            binRepository.removeBinFromHistory(bin)
        }
    }

    fun clearHistory() {
        viewModelScope.launch { binRepository.clearBinsHistory() }
    }
}