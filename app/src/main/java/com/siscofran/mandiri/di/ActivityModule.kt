package com.siscofran.mandiri.di

import com.siscofran.mandiri.ui.main.MainFragmentModule
import com.siscofran.mandiri.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun bindMainActivity(): MainActivity

}