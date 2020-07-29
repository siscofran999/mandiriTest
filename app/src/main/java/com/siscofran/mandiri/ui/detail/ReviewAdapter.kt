package com.siscofran.mandiri.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.siscofran.mandiri.BR
import com.siscofran.mandiri.R
import com.siscofran.mandiri.data.model.ResultY
import com.siscofran.mandiri.databinding.ItemReviewBinding

class ReviewAdapter(private val result: ArrayList<ResultY>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ReviewHolder(DataBindingUtil.inflate<ItemReviewBinding>(LayoutInflater.from(parent.context),R.layout.item_review, parent, false))

    override fun getItemCount(): Int = result.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ReviewHolder).binding.apply {
            setVariable(BR.review, result[position])
        }
    }

    fun refreshAdapter(it: ArrayList<ResultY>) {
        result.addAll(it)
        notifyDataSetChanged()
    }

    class ReviewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}