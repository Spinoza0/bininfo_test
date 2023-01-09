package com.spinoza.bininfotest.domain

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable

@Dao
interface BinInfoDao {
    @Query("SELECT * FROM bins ORDER BY value")
    fun getHistory(): LiveData<List<Bin>>

    @Insert
    fun insertToHistory(bin: Bin): Completable

    @Query("DELETE FROM bins")
    fun clearHistory(): Completable
}