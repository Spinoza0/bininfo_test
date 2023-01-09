package com.spinoza.bininfotest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spinoza.bininfotest.domain.BinApiService
import com.spinoza.bininfotest.domain.BinInfo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class BinInfoViewModel(private val apiService: BinApiService) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val binInfo = MutableLiveData<BinInfo>()
    private val isLoading = MutableLiveData(false)
    private val isError: MutableLiveData<String> = MutableLiveData()

    fun getBinInfo(): LiveData<BinInfo> = binInfo
    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getIsError(): LiveData<String> = isError

    fun load(binValue: String) {
        val loading = isLoading.value
        loading?.let {
            if (!it) {
                val disposable: Disposable = apiService.getBinInfo(binValue)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { isLoading.value = true }
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate { isLoading.value = false }
                    .subscribe({ loadedBinInfo -> binInfo.value = loadedBinInfo })
                    { throwable -> isError.value = throwable.message }
                compositeDisposable.add(disposable)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}