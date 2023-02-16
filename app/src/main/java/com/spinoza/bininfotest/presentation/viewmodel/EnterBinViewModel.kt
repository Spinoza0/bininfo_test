package com.spinoza.bininfotest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.bininfotest.domain.model.Bin
import com.spinoza.bininfotest.domain.usecase.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class EnterBinViewModel @Inject constructor(
    getBinsHistoryUseCase: GetBinsHistoryUseCase,
    isErrorUseCase: IsErrorUseCase,
    private val insertBinToHistoryUseCase: InsertBinToHistoryUseCase,
    private val removeBinFromHistoryUseCase: RemoveBinFromHistoryUseCase,
    private val cleanBinsHistoryUseCase: CleanBinsHistoryUseCase,
) : ViewModel() {

    val history = getBinsHistoryUseCase()
    val isError = isErrorUseCase()

    fun insertToHistory(bin: Bin) {
        viewModelScope.launch {
            insertBinToHistoryUseCase(bin)
        }
    }

    fun removeFromHistory(bin: Bin) {
        viewModelScope.launch {
            removeBinFromHistoryUseCase(bin)
        }
    }

    fun clearHistory() {
        viewModelScope.launch { cleanBinsHistoryUseCase }
    }
}