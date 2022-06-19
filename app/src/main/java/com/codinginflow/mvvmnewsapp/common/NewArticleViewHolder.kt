package com.codinginflow.mvvmnewsapp.common

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codinginflow.mvvmnewsapp.R
import com.codinginflow.mvvmnewsapp.data.NewsArticle
import com.codinginflow.mvvmnewsapp.databinding.DisplayItemListBinding

class NewArticleViewHolder(
    private val binding: DisplayItemListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(newsArticle: NewsArticle) {
        with(binding) {
            Glide.with(itemView)
                .load(newsArticle.thumbnailUrl)
                .error(R.drawable.image_placeholder)
                .into(imageView)

            textViewTitle.text = newsArticle.title ?: ""

            imageViewBookmark.setImageResource(
                when {
                    newsArticle.isBookmarked -> R.drawable.ic_bookmark_selected
                    else -> R.drawable.ic_bookmark_unselected
                }
            )
        }

    }
}