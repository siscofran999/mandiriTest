package com.siscofran.mandiri.ui.discover

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.siscofran.mandiri.R
import com.siscofran.mandiri.ViewModelProviderFactory
import com.siscofran.mandiri.ui.main.MainAdapter
import com.siscofran.mandiri.ui.main.MainViewModel
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_discover.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import javax.inject.Inject

class DiscoverActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var viewModel: DiscoverViewModel
    var discoverAdapter: DiscoverAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(DiscoverViewModel::class.java)
        toolbar.title = getString(R.string.label_discover)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val idGenre = intent.getIntExtra(GENRE, 0)

        viewModel.getDiscover(idGenre)

        viewModel.discover().observe(this, Observer {
            rv_discover.layoutManager = LinearLayoutManager(this)
            discoverAdapter = DiscoverAdapter(it)
            discoverAdapter?.setHasStableIds(true)
            rv_discover.adapter = discoverAdapter
            discoverAdapter?.notifyDataSetChanged()
        })

        viewModel.error().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        rv_discover.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val countItem = linearLayoutManager.itemCount
                val lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                val isLastPosition = countItem.minus(1) == lastVisiblePosition
                if(isLastPosition){
                    viewModel.loadMore(idGenre)
                }
            }
        })

        viewModel.loadMoreDiscover().observe(this, Observer {
            discoverAdapter?.refreshAdapter(it)
        })

    }

    companion object{
        const val GENRE = "genre"
    }
}