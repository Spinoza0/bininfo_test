package com.spinoza.bininfotest.di

import android.content.Context
import com.spinoza.bininfotest.data.database.DataBase
import com.spinoza.bininfotest.data.database.HistoryDao
import dagger.Module
import dagger.Provides

@Module
class DataModule() {

    @ApplicationScope
    @Provides
    fun provideHistoryDao(context: Context): HistoryDao = DataBase.getInstance(context).historyDao()
}