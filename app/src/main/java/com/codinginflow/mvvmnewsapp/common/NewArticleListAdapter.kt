package com.codinginflow.mvvmnewsapp.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.codinginflow.mvvmnewsapp.data.NewsArticle
import com.codinginflow.mvvmnewsapp.databinding.DisplayItemListBinding

class NewArticleListAdapter :
    ListAdapter<NewsArticle, NewArticleViewHolder>(NewArticleComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewArticleViewHolder {
        val binding = DisplayItemListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NewArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewArticleViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let { holder.bind(it) }
    }
}