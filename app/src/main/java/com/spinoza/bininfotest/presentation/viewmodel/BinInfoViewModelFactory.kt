package com.spinoza.bininfotest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.bininfotest.domain.ApiService

class BinInfoViewModelFactory(private val apiService: ApiService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(ApiService::class.java)
            .newInstance(apiService)
    }
}