package com.siscofran.mandiri.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.siscofran.mandiri.R
import com.siscofran.mandiri.ViewModelProviderFactory
import com.siscofran.mandiri.ui.discover.DiscoverAdapter
import com.siscofran.mandiri.ui.reviews.ReviewsActivity
import com.siscofran.mandiri.util.CommonUtil
import com.siscofran.mandiri.util.Link
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.toolbar
import kotlinx.android.synthetic.main.activity_discover.*
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(DetailViewModel::class.java)
        toolbar.title = getString(R.string.label_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val movieId = intent.getIntExtra(MOVIE_ID, 0)
        viewModel.getDetail(movieId)

        viewModel.detail().observe(this, Observer {
            Glide.with(application)
                .load(Link.BASE_IMG +it.backdrop_path)
                .into(img)
            txv_genre.text = CommonUtil().formatGenrelistToSpan(it.genres)
            txv_overview.text = it.overview
            txv_title.text = it.title
        })

        val mediaController = MediaController(this)
        mediaController.setAnchorView(video_view)

        viewModel.getVideo(movieId)
        viewModel.video().observe(this, Observer {
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
                Toast.makeText(this@DetailActivity, "Maaf, video tidak tersedia pada movie ini", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.error().observe(this, Observer {
            Toast.makeText(this@DetailActivity, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.getReview(movieId)
        viewModel.review().observe(this, Observer {
            when {
                it.total_results == 0 -> {
                    rv_review.visibility = View.GONE
                    btn_see_all_review.visibility = View.GONE
                    Toast.makeText(this@DetailActivity, "Maaf, tidak ada review pada movie ini", Toast.LENGTH_SHORT).show()
                }
                it.total_results <= 3 -> {
                    btn_see_all_review.visibility = View.GONE
                    Toast.makeText(this@DetailActivity, "total review ${it.total_results} movie ini", Toast.LENGTH_SHORT).show()
                    val review = ArrayList(it.results.take(3))
                    rv_review.layoutManager = LinearLayoutManager(this@DetailActivity)
                    val reviewAdapter = ReviewAdapter(review)
                    rv_review.adapter = reviewAdapter
                    reviewAdapter.notifyDataSetChanged()
                }
                else -> {
                    val review = ArrayList(it.results.take(3))
                    rv_review.layoutManager = LinearLayoutManager(this@DetailActivity)
                    val reviewAdapter = ReviewAdapter(review)
                    rv_review.adapter = reviewAdapter
                    reviewAdapter.notifyDataSetChanged()
                }
            }
        })

        btn_see_all_review.setOnClickListener {
            val intent = Intent(this@DetailActivity, ReviewsActivity::class.java)
            intent.putExtra(MOVIE_ID, movieId)
            startActivity(intent)
        }
    }

    companion object{
        const val MOVIE_ID = "movieId"
    }
}