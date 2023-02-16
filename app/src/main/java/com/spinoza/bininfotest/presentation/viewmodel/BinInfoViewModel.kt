package com.spinoza.bininfotest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.bininfotest.domain.usecase.GetBinInfoUseCase
import com.spinoza.bininfotest.domain.usecase.IsErrorUseCase
import com.spinoza.bininfotest.domain.usecase.IsLoadingUseCase
import com.spinoza.bininfotest.domain.usecase.LoadBinInfoUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class BinInfoViewModel @Inject constructor(
    getBinInfoUseCase: GetBinInfoUseCase,
    isLoadingUseCase: IsLoadingUseCase,
    isErrorUseCase: IsErrorUseCase,
    private val loadBinInfoUseCase: LoadBinInfoUseCase,
) : ViewModel() {

    val binInfo = getBinInfoUseCase()
    val isLoading = isLoadingUseCase()
    val isError = isErrorUseCase()

    fun loadBinInfo(binValue: String) {
        viewModelScope.launch {
            loadBinInfoUseCase(binValue)
        }
    }
}