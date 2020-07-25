package com.siscofran.mandiri.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.siscofran.mandiri.R
import com.siscofran.mandiri.data.model.ResultY
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewAdapter(private val result: ArrayList<ResultY>) : RecyclerView.Adapter<ReviewAdapter.ReviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder =
        ReviewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false))

    override fun getItemCount(): Int = result.size

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) = holder.bindReview(result[position])

    fun refreshAdapter(it: ArrayList<ResultY>) {
        result.addAll(it)
        notifyDataSetChanged()
    }

    class ReviewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindReview(resultY: ResultY) {
            itemView.txv_author.text = resultY.author
            itemView.txv_content.text = resultY.content
        }

    }
}