package com.spinoza.bininfotest.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = HistoryDao.TABLE_HISTORY)
data class BinDbModel(
    @PrimaryKey
    val value: String,
) : java.io.Serializable