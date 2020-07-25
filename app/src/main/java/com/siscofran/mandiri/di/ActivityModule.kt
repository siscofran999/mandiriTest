package com.siscofran.mandiri.di

import com.siscofran.mandiri.ui.detail.DetailActivity
import com.siscofran.mandiri.ui.discover.DiscoverActivity
import com.siscofran.mandiri.ui.main.MainActivity
import com.siscofran.mandiri.ui.reviews.ReviewsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindDiscoverActivity(): DiscoverActivity

    @ContributesAndroidInjector
    abstract fun bindDetailActivity(): DetailActivity

    @ContributesAndroidInjector
    abstract fun bindReviewsActivity(): ReviewsActivity
}