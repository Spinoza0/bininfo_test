package com.spinoza.bininfotest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.bininfotest.domain.repository.BinRepository

class BinInfoViewModelFactory(private val binRepository: BinRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(BinRepository::class.java)
            .newInstance(binRepository)
    }
}