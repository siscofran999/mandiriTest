package com.siscofran.mandiri.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.siscofran.mandiri.BR
import com.siscofran.mandiri.R
import com.siscofran.mandiri.data.model.GenreX
import com.siscofran.mandiri.databinding.ItemGenreBinding
import com.siscofran.mandiri.ui.discover.DiscoverFragment.Companion.GENRE

class MainAdapter(private val genreX: List<GenreX>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        GenreViewHolder(DataBindingUtil.inflate<ItemGenreBinding>(LayoutInflater.from(parent.context), R.layout.item_genre, parent, false))


    override fun getItemCount(): Int = genreX.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GenreViewHolder).binding.apply {
            setVariable(BR.genreX, genreX[position])
            holder.itemView.setOnClickListener {
                val bundle = bundleOf(GENRE to genreX[position].id)
                it.findNavController().navigate(R.id.action_mainFragment_to_discoverFragment, bundle)
            }
        }
    }

    class GenreViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

}