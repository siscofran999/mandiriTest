package com.siscofran.mandiri.ui.main

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
import com.siscofran.mandiri.R
import com.siscofran.mandiri.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject


class MainFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var navController: NavController
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(MainViewModel::class.java)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        viewModel.getGenre()
        activity?.let {
            viewModel.genre().observe(it, Observer { genreX ->
                rv_genre.layoutManager = LinearLayoutManager(it)
                val mainAdapter = MainAdapter(genreX)
                rv_genre.adapter = mainAdapter
                mainAdapter.notifyDataSetChanged()
            })

            viewModel.error().observe(it, Observer {message ->
                Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
            })
        }
    }

}