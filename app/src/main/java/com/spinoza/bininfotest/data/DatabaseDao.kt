package com.spinoza.bininfotest.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.spinoza.bininfotest.domain.Bin
import com.spinoza.bininfotest.domain.BinInfoDao

@Dao
interface DatabaseDao : BinInfoDao {
    @Query("SELECT * FROM bins ORDER BY value")
    override fun getHistory(): LiveData<List<Bin>>

    @Insert
    override suspend fun insertToHistory(bin: Bin)

    @Query("DELETE FROM bins")
    override suspend fun clearHistory()
}