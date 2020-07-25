package com.siscofran.mandiri.di

import com.siscofran.mandiri.data.ApiHelper
import com.siscofran.mandiri.data.ApiRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindsApiRepository(repository: ApiRepository): ApiHelper
}