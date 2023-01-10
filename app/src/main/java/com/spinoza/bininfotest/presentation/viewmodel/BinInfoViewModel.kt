package com.spinoza.bininfotest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spinoza.bininfotest.domain.ApiService
import com.spinoza.bininfotest.domain.BinInfo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class BinInfoViewModel(private val apiService: ApiService) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
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
        val loading = _isLoading.value
        loading?.let {
            if (!it) {
                val disposable: Disposable = apiService.getBinInfo(binValue)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { _isLoading.value = true }
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate { _isLoading.value = false }
                    .subscribe({ loadedBinInfo -> _binInfo.value = loadedBinInfo })
                    { throwable -> _isError.value = throwable.message }
                compositeDisposable.add(disposable)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}