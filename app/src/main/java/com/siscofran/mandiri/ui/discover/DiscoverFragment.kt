package com.siscofran.mandiri.ui.discover

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
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_discover.*
import javax.inject.Inject

class DiscoverFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var navController: NavController
    lateinit var viewModel: DiscoverViewModel
    var discoverAdapter: DiscoverAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.label_discover)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(DiscoverViewModel::class.java)
        val idGenre = arguments?.getInt(GENRE)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        idGenre?.let { viewModel.getDiscover(it) }

        activity?.let {
            viewModel.discover().observe(it, Observer {result ->
                rv_discover.layoutManager = LinearLayoutManager(it)
                discoverAdapter = DiscoverAdapter(result)
                discoverAdapter?.setHasStableIds(true)
                rv_discover.adapter = discoverAdapter
                discoverAdapter?.notifyDataSetChanged()
            })

            viewModel.error().observe(it, Observer {message ->
                Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
            })

            rv_discover.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val countItem = linearLayoutManager.itemCount
                    val lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                    val isLastPosition = countItem.minus(1) == lastVisiblePosition
                    if(isLastPosition){
                        idGenre?.let { it1 -> viewModel.loadMore(it1) }
                    }
                }
            })

            viewModel.loadMoreDiscover().observe(it, Observer {result ->
                discoverAdapter?.refreshAdapter(result)
            })
        }
    }

    companion object{
        const val GENRE = "genre"
    }
    
}