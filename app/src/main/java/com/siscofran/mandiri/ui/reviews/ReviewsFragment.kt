package com.siscofran.mandiri.ui.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.siscofran.mandiri.R
import com.siscofran.mandiri.ViewModelProviderFactory
import com.siscofran.mandiri.ui.detail.DetailFragment
import com.siscofran.mandiri.ui.detail.ReviewAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_discover.toolbar
import kotlinx.android.synthetic.main.fragment_reviews.*
import javax.inject.Inject


class ReviewsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var navController: NavController
    lateinit var viewModel: ReviewsViewModel
    var reviewAdapter: ReviewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(ReviewsViewModel::class.java)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.label_reviews)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        val movieId = arguments?.getInt(DetailFragment.MOVIE_ID)
        movieId?.let { viewModel.getReviews(it) }

        activity?.let {context ->
            viewModel.review().observe(context, Observer {
                rv_review.layoutManager = LinearLayoutManager(context)
                reviewAdapter = ReviewAdapter(it)
                reviewAdapter?.setHasStableIds(true)
                rv_review.adapter = reviewAdapter
                reviewAdapter?.notifyDataSetChanged()
            })

            viewModel.error().observe(context, Observer {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })

            rv_review.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val countItem = linearLayoutManager.itemCount
                    val lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                    val isLastPosition = countItem.minus(1) == lastVisiblePosition
                    if(isLastPosition){
                        movieId?.let { viewModel.loadMore(it) }
                    }
                }
            })

            viewModel.loadMoreReviews().observe(context, Observer {
                reviewAdapter?.refreshAdapter(it)
            })
        }
    }

}