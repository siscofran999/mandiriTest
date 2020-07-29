package com.siscofran.mandiri.util

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.ImageView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.siscofran.mandiri.R
import com.siscofran.mandiri.data.model.GenreY
import com.siscofran.mandiri.util.Link.BASE_IMG

fun formatGenrelistToSpan(genreList: List<GenreY>): Spanned? {
    var spanned: Spanned? = null
    if (genreList != null && genreList.size > 0) {
        var result = ""
        //val color: String = genreList[0].getColor()
        for (i in genreList.indices) {
            val genre: GenreY = genreList[i]
            val title: String = genre.name
            result = if (i == genreList.size - 1) {
                result + title
            } else {
                "$result$title - "
            }
            spanned = if (Build.VERSION.SDK_INT >= 24) {
                Html.fromHtml(result, Html.FROM_HTML_MODE_LEGACY)
            } else {
                HtmlCompat.fromHtml(result, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
        }
    }
    return spanned
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(BASE_IMG+url)
        .placeholder(R.drawable.ic_launcher_background)
        .into(view)
}