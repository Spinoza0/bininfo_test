package com.spinoza.bininfotest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spinoza.bininfotest.domain.model.BinInfo
import com.spinoza.bininfotest.domain.repository.BinRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class BinInfoViewModel @Inject constructor(private val binRepository: BinRepository) : ViewModel() {

    private val _binInfo = MutableLiveData<BinInfo>()
    private val _isLoading = MutableLiveData(false)
    private val _isError: MutableLiveData<String> = MutableLiveData()

    val binInfo: LiveData<BinInfo>
        get() = _binInfo
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    val isError: LiveData<String>
        get() = _isError


    fun load(binValue: String) {
        viewModelScope.launch {
            _isLoading.value?.let {
                if (!it) {
                    _isLoading.value = true
                    try {
                        val binInfo = binRepository.getBinInfo(binValue)
                        _binInfo.value = binInfo
                    } catch (e: Exception) {
                        _isError.value = e.message
                    }
                    _isLoading.value = false
                }
            }
        }
    }
}