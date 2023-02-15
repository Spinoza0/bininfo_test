package com.spinoza.bininfotest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.bininfotest.domain.repository.BinRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class BinInfoViewModel @Inject constructor(private val binRepository: BinRepository) : ViewModel() {

    val binInfo = binRepository.getBinInfo()
    val isLoading = binRepository.isLoading()
    val isError = binRepository.isError()

    fun loadBinInfo(binValue: String) {
        viewModelScope.launch {
            binRepository.loadBinInfo(binValue)
        }
    }
}