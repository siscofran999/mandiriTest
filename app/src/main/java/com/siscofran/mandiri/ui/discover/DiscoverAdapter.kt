package com.siscofran.mandiri.ui.discover

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.siscofran.mandiri.R
import com.siscofran.mandiri.data.model.Result
import com.siscofran.mandiri.ui.detail.DetailActivity
import com.siscofran.mandiri.ui.detail.DetailActivity.Companion.MOVIE_ID
import com.siscofran.mandiri.util.Link.BASE_IMG
import kotlinx.android.synthetic.main.item_discover.view.*

class DiscoverAdapter(private val result: ArrayList<Result>) : RecyclerView.Adapter<DiscoverAdapter.DiscoverHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverHolder =
        DiscoverHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_discover, parent, false))

    override fun getItemCount(): Int = result.size

    override fun onBindViewHolder(holder: DiscoverHolder, position: Int) = holder.bindDiscover(result[position])

    override fun getItemId(position: Int): Long {
        return result[position].id.toLong()
    }

    fun refreshAdapter(it: ArrayList<Result>) {
        result.addAll(it)
        notifyDataSetChanged()
    }

    class DiscoverHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindDiscover(result: Result) {
            Glide.with(itemView.context)
                .load(BASE_IMG+result.poster_path)
                .apply(RequestOptions().override(300))
                .into(itemView.img)

            itemView.txv_title.text = result.title
            itemView.txv_overview.text = result.overview
            itemView.txv_release.text = result.release_date

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(MOVIE_ID, result.id)
                itemView.context.startActivity(intent)
            }
        }

    }
}