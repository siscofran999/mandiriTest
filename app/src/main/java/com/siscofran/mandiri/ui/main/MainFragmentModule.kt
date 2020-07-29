package com.siscofran.mandiri.ui.main

import com.siscofran.mandiri.ui.detail.DetailFragment
import com.siscofran.mandiri.ui.discover.DiscoverFragment
import com.siscofran.mandiri.ui.reviews.ReviewsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentModule {

    @ContributesAndroidInjector
    abstract fun provideMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun provideDiscoverFragment(): DiscoverFragment

    @ContributesAndroidInjector
    abstract fun provideDetailFragment(): DetailFragment

    @ContributesAndroidInjector
    abstract fun provideReviewsFragment(): ReviewsFragment

}