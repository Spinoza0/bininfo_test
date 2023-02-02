package com.spinoza.bininfotest.di

import com.spinoza.bininfotest.data.repository.BinRepositoryImpl
import com.spinoza.bininfotest.data.repository.HistoryRepositoryImpl
import com.spinoza.bininfotest.domain.repository.BinRepository
import com.spinoza.bininfotest.domain.repository.HistoryRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @ApplicationScope
    @Binds
    fun bindBinRepository(impl: BinRepositoryImpl): BinRepository

    @ApplicationScope
    @Binds
    fun bindHistoryRepository(impl: HistoryRepositoryImpl): HistoryRepository
}