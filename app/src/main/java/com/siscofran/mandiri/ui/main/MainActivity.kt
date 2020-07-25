package com.siscofran.mandiri.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.siscofran.mandiri.R
import com.siscofran.mandiri.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(MainViewModel::class.java)
        viewModel.getGenre()

        viewModel.genre().observe(this@MainActivity, Observer { genreX ->
            rv_genre.layoutManager = LinearLayoutManager(this@MainActivity)
            val mainAdapter = MainAdapter(genreX)
            rv_genre.adapter = mainAdapter
            mainAdapter.notifyDataSetChanged()
        })

        viewModel.error().observe(this@MainActivity, Observer {
            Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
        })
    }
}