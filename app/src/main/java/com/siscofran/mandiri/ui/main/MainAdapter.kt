package com.siscofran.mandiri.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.siscofran.mandiri.R
import com.siscofran.mandiri.data.model.GenreX
import com.siscofran.mandiri.ui.discover.DiscoverActivity
import kotlinx.android.synthetic.main.item_genre.view.*

class MainAdapter(private val genreX: List<GenreX>) : RecyclerView.Adapter<MainAdapter.GenreHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreHolder =
        GenreHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false))

    override fun getItemCount(): Int = genreX.size

    override fun onBindViewHolder(holder: GenreHolder, position: Int) = holder.bindGenre(genreX[position])

    class GenreHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindGenre(genreX: GenreX) {
            itemView.txv_genre.text = genreX.name

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DiscoverActivity::class.java)
                intent.putExtra(DiscoverActivity.GENRE, genreX.id)
                itemView.context.startActivity(intent)
            }
        }

    }
}