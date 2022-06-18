package com.codinginflow.mvvmnewsapp.data

import com.codinginflow.mvvmnewsapp.api.Api
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi : Api,
    private val db : NewsArticleDatabase
) {
    private val newsArticleDao = db.newArticleDao()
}

