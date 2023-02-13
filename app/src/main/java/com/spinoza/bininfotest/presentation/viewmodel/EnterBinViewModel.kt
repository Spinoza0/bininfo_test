package com.spinoza.bininfotest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.bininfotest.domain.model.Bin
import com.spinoza.bininfotest.domain.repository.BinRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class EnterBinViewModel @Inject constructor(private val binRepository: BinRepository) :
    ViewModel() {

    val history = binRepository.getHistory()

    private val _isError: MutableLiveData<String> = MutableLiveData()
    val isError: LiveData<String>
        get() = _isError

    fun insertToHistory(bin: Bin) {
        viewModelScope.launch {
            var needToInsert = true
            history.value?.let { needToInsert = !it.contains(bin) }
            if (needToInsert) {
                binRepository.insertToHistory(bin)
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch { binRepository.clearHistory() }
    }
}