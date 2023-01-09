package com.spinoza.bininfotest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.bininfotest.domain.BinApiService

class BinInfoViewModelFactory(private val apiService: BinApiService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(BinApiService::class.java)
            .newInstance(apiService)
    }
}