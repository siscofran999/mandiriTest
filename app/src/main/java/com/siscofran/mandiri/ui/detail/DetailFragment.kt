package com.siscofran.mandiri.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.siscofran.mandiri.R
import com.siscofran.mandiri.ViewModelProviderFactory
import com.siscofran.mandiri.util.Link
import com.siscofran.mandiri.util.formatGenrelistToSpan
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_discover.toolbar
import javax.inject.Inject


class DetailFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var navController: NavController
    lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(DetailViewModel::class.java)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.label_detail)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        val movieId = arguments?.getInt(MOVIE_ID)
        movieId?.let { viewModel.getDetail(it) }

        activity?.let {context ->
            viewModel.detail().observe(context, Observer {detail ->
                Glide.with(context)
                    .load(Link.BASE_IMG +detail.backdrop_path)
                    .into(img)
                txv_genre.text = formatGenrelistToSpan(detail.genres)
                txv_overview.text = detail.overview
                txv_title.text = detail.title
            })

            val mediaController = MediaController(context)
            mediaController.setAnchorView(video_view)

            movieId?.let { it1 -> viewModel.getVideo(it1) }
            viewModel.video().observe(context, Observer {
                if(it != ""){
                    lifecycle.addObserver(video_view)
                    video_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            super.onReady(youTubePlayer)
                            youTubePlayer.loadVideo(it, 0f)
                        }
                    })
                }else{
                    video_view.visibility = View.GONE
                    Toast.makeText(context, "Maaf, video tidak tersedia pada movie ini", Toast.LENGTH_SHORT).show()
                }
            })

            viewModel.error().observe(context, Observer {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })

            movieId?.let { viewModel.getReview(it) }
            viewModel.review().observe(context, Observer {
                when {
                    it.total_results == 0 -> {
                        rv_review.visibility = View.GONE
                        btn_see_all_review.visibility = View.GONE
                        Toast.makeText(context, "Maaf, tidak ada review pada movie ini", Toast.LENGTH_SHORT).show()
                    }
                    it.total_results <= 3 -> {
                        btn_see_all_review.visibility = View.GONE
                        Toast.makeText(context, "total review ${it.total_results} movie ini", Toast.LENGTH_SHORT).show()
                        val review = ArrayList(it.results.take(3))
                        rv_review.layoutManager = LinearLayoutManager(context)
                        val reviewAdapter = ReviewAdapter(review)
                        rv_review.adapter = reviewAdapter
                        reviewAdapter.notifyDataSetChanged()
                    }
                    else -> {
                        val review = ArrayList(it.results.take(3))
                        rv_review.layoutManager = LinearLayoutManager(context)
                        val reviewAdapter = ReviewAdapter(review)
                        rv_review.adapter = reviewAdapter
                        reviewAdapter.notifyDataSetChanged()
                    }
                }
            })

            btn_see_all_review.setOnClickListener {
                val bundle = bundleOf(MOVIE_ID to movieId)
                it.findNavController().navigate(R.id.action_detailFragment_to_reviewsFragment, bundle)
            }
        }
    }

    companion object{
        const val MOVIE_ID = "movieId"
    }
}