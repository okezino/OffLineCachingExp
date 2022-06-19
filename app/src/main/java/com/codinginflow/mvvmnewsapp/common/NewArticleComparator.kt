package com.codinginflow.mvvmnewsapp.common

import androidx.recyclerview.widget.DiffUtil
import com.codinginflow.mvvmnewsapp.data.NewsArticle

class NewArticleComparator : DiffUtil.ItemCallback<NewsArticle>() {
    override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
        return oldItem == newItem
    }
}