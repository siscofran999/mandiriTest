package com.siscofran.mandiri.ui.reviews

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.siscofran.mandiri.R
import com.siscofran.mandiri.ViewModelProviderFactory
import com.siscofran.mandiri.ui.detail.DetailActivity
import com.siscofran.mandiri.ui.detail.ReviewAdapter
import com.siscofran.mandiri.ui.discover.DiscoverAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_discover.*
import kotlinx.android.synthetic.main.activity_reviews.*
import kotlinx.android.synthetic.main.activity_reviews.toolbar
import javax.inject.Inject

class ReviewsActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var viewModel: ReviewsViewModel
    var reviewAdapter: ReviewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)

        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(ReviewsViewModel::class.java)
        toolbar.title = getString(R.string.label_reviews)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val movieId = intent.getIntExtra(DetailActivity.MOVIE_ID, 0)
        viewModel.getReviews(movieId)

        viewModel.review().observe(this, Observer {
            rv_review.layoutManager = LinearLayoutManager(this@ReviewsActivity)
            reviewAdapter = ReviewAdapter(it)
            reviewAdapter?.setHasStableIds(true)
            rv_review.adapter = reviewAdapter
            reviewAdapter?.notifyDataSetChanged()
        })

        viewModel.error().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        rv_review.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val countItem = linearLayoutManager.itemCount
                val lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                val isLastPosition = countItem.minus(1) == lastVisiblePosition
                if(isLastPosition){
                    viewModel.loadMore(movieId)
                }
            }
        })

        viewModel.loadMoreReviews().observe(this, Observer {
            reviewAdapter?.refreshAdapter(it)
        })
    }
}