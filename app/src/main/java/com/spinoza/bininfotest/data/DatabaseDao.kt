package com.spinoza.bininfotest.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.spinoza.bininfotest.domain.Bin
import com.spinoza.bininfotest.domain.BinInfoDao
import io.reactivex.rxjava3.core.Completable

@Dao
interface DatabaseDao : BinInfoDao {
    @Query("SELECT * FROM bins ORDER BY value")
    override fun getHistory(): LiveData<List<Bin>>

    @Insert
    override fun insertToHistory(bin: Bin): Completable

    @Query("DELETE FROM bins")
    override fun clearHistory(): Completable
}