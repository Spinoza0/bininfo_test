package com.spinoza.bininfotest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.spinoza.bininfotest.domain.Bin

@Database(entities = [Bin::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    companion object {
        private const val DB_NAME = "bininfotest.db"
        private var db: DataBase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): DataBase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(context, DataBase::class.java, DB_NAME).build()
                db = instance
                return instance
            }
        }
    }

    abstract fun binInfoDao(): DatabaseDao
}