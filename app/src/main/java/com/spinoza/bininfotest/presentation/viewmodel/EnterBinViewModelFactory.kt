package com.spinoza.bininfotest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.bininfotest.domain.repository.HistoryRepository

class EnterBinViewModelFactory(private val historyRepository: HistoryRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(HistoryRepository::class.java)
            .newInstance(historyRepository)
    }
}