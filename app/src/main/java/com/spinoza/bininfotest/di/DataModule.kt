package com.spinoza.bininfotest.di

import android.content.Context
import com.spinoza.bininfotest.data.database.DataBase
import com.spinoza.bininfotest.data.database.HistoryDao
import com.spinoza.bininfotest.data.network.ApiFactory
import com.spinoza.bininfotest.data.network.ApiService
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    companion object {
        @ApplicationScope
        @Provides
        fun provideHistoryDao(context: Context): HistoryDao =
            DataBase.getInstance(context).historyDao()

        @ApplicationScope
        @Provides
        fun provideBinApiService(): ApiService = ApiFactory.apiService
    }
}