package com.siscofran.mandiri.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.siscofran.mandiri.ViewModelProviderFactory
import com.siscofran.mandiri.ui.detail.DetailViewModel
import com.siscofran.mandiri.ui.discover.DiscoverViewModel
import com.siscofran.mandiri.ui.main.MainViewModel
import com.siscofran.mandiri.ui.reviews.ReviewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DiscoverViewModel::class)
    abstract fun bindDiscoverViewModel(discoverViewModel: DiscoverViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(detailViewModel: DetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReviewsViewModel::class)
    abstract fun bindReviewsViewModel(reviewsViewModel: ReviewsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory?
}
