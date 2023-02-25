package com.spinoza.bininfotest.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {

    @Query("SELECT * FROM $TABLE_HISTORY ORDER BY value")
    suspend fun getHistory(): List<BinDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToHistory(bin: BinDbModel)

    @Query("DELETE FROM $TABLE_HISTORY WHERE value=:value")
    suspend fun removeFromHistory(value: String)

    @Query("DELETE FROM $TABLE_HISTORY")
    suspend fun clearHistory()

    companion object {
        const val TABLE_HISTORY = "bins"
    }
}