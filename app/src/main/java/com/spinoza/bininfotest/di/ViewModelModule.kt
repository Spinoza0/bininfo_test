package com.spinoza.bininfotest.di

import androidx.lifecycle.ViewModel
import com.spinoza.bininfotest.presentation.viewmodel.BinInfoViewModel
import com.spinoza.bininfotest.presentation.viewmodel.EnterBinViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(BinInfoViewModel::class)
    @Binds
    fun bindBinInfoViewModel(impl: BinInfoViewModel): ViewModel

    @IntoMap
    @ViewModelKey(EnterBinViewModel::class)
    @Binds
    fun bindEnterBinViewModel(impl: EnterBinViewModel): ViewModel
}