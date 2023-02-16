package com.spinoza.bininfotest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.bininfotest.domain.usecase.GetStateUseCase
import com.spinoza.bininfotest.domain.usecase.LoadBinInfoUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class BinInfoViewModel @Inject constructor(
    getStateUseCase: GetStateUseCase,
    private val loadBinInfoUseCase: LoadBinInfoUseCase,
) : ViewModel() {

    val state = getStateUseCase()

    fun loadBinInfo(binValue: String) {
        viewModelScope.launch {
            loadBinInfoUseCase(binValue)
        }
    }
}