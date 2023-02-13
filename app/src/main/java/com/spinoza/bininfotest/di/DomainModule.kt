package com.spinoza.bininfotest.di

import com.spinoza.bininfotest.data.repository.BinRepositoryImpl
import com.spinoza.bininfotest.domain.repository.BinRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @ApplicationScope
    @Binds
    fun bindBinRepository(impl: BinRepositoryImpl): BinRepository
}