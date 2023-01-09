package com.spinoza.bininfotest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spinoza.bininfotest.domain.Bin
import com.spinoza.bininfotest.domain.BinInfoDao
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class EnterBinViewModel(private val db: BinInfoDao) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val historyListLiveData = db.getHistory()

    private val isError: MutableLiveData<String> = MutableLiveData()
    fun getIsError(): LiveData<String> = isError

    fun insertToHistory(bin: Bin) {
        var needToInsert = true
        historyListLiveData.value?.let { needToInsert = !it.contains(bin) }
        if (needToInsert) {
            val disposable: Disposable = db.insertToHistory(bin)
                .subscribeOn(Schedulers.io())
                .subscribe({}) { throwable -> isError.value = throwable.message }
            compositeDisposable.add(disposable)
        }
    }

    fun clearHistory() {
        val disposable: Disposable = db.clearHistory()
            .subscribeOn(Schedulers.io())
            .subscribe({}) { throwable -> isError.value = throwable.message }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}