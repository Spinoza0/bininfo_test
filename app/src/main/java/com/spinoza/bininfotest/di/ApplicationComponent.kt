package com.spinoza.bininfotest.di

import android.content.Context
import com.spinoza.bininfotest.presentation.BinInfoActivity
import com.spinoza.bininfotest.presentation.EnterBinActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DomainModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: EnterBinActivity)
    fun inject(activity: BinInfoActivity)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}