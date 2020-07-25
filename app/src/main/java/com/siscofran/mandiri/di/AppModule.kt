package com.siscofran.mandiri.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.siscofran.mandiri.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}