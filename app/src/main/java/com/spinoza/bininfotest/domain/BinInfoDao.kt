package com.spinoza.bininfotest.domain

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Completable

interface BinInfoDao {
    fun getHistory(): LiveData<List<Bin>>
    fun insertToHistory(bin: Bin): Completable
    fun clearHistory(): Completable
}