package com.spinoza.bininfotest.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {
    @Query("SELECT * FROM bins ORDER BY value")
    fun getHistory(): LiveData<List<BinDbModel>>

    @Insert
    suspend fun insertToHistory(bin: BinDbModel)

    @Query("DELETE FROM bins")
    suspend fun clearHistory()
}