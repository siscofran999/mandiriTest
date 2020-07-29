package com.siscofran.mandiri.ui.discover

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.siscofran.mandiri.BR
import com.siscofran.mandiri.R
import com.siscofran.mandiri.data.model.Result
import com.siscofran.mandiri.databinding.ItemDiscoverBinding
import com.siscofran.mandiri.ui.detail.DetailFragment.Companion.MOVIE_ID
import com.siscofran.mandiri.util.Link.BASE_IMG
import kotlinx.android.synthetic.main.item_discover.view.*

class DiscoverAdapter(private val result: ArrayList<Result>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        DiscoverHolder(DataBindingUtil.inflate<ItemDiscoverBinding>(LayoutInflater.from(parent.context),R.layout.item_discover, parent, false))

    override fun getItemCount(): Int = result.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DiscoverHolder).binding.apply {
            setVariable(BR.discover, result[position])
            holder.itemView.setOnClickListener {
                val bundle = bundleOf(MOVIE_ID to result[position].id)
                it.findNavController().navigate(R.id.action_discoverFragment_to_detailFragment, bundle)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return result[position].id.toLong()
    }

    fun refreshAdapter(it: ArrayList<Result>) {
        result.addAll(it)
        notifyDataSetChanged()
    }

    class DiscoverHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}