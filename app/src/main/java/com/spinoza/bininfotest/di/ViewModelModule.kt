package com.spinoza.bininfotest.di

import androidx.lifecycle.ViewModel
import com.spinoza.bininfotest.presentation.viewmodel.BinInfoViewModel
import com.spinoza.bininfotest.presentation.viewmodel.EnterBinViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
interface ViewModelModule {
    @IntoMap
    @StringKey("BinInfoViewModel")
    @Binds
    fun bindBinInfoViewModel(impl: BinInfoViewModel): ViewModel

    @IntoMap
    @StringKey("EnterBinViewModel")
    @Binds
    fun bindEnterBinViewModel(impl: EnterBinViewModel): ViewModel
}