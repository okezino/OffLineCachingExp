package com.codinginflow.mvvmnewsapp.data

import android.util.Log
import androidx.room.withTransaction
import com.codinginflow.mvvmnewsapp.api.Api
import com.codinginflow.mvvmnewsapp.common.Resource
import com.codinginflow.mvvmnewsapp.common.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.lang.Thread.currentThread
import javax.inject.Inject

private const val TAG = "NewsRepository"

class NewsRepository @Inject constructor(
    private val newsApi: Api,
    private val db: NewsArticleDatabase
) {
    private val newsArticleDao = db.newArticleDao()


    fun getBreakingNews(): Flow<Resource<List<NewsArticle>>> =
        networkBoundResource(
            query = { newsArticleDao.getAllBreakingNews() },
            fetch = { newsApi.getBreakingNews().articles },
            saveFetchResult = { serverArticles ->
                val breakingArticles = serverArticles.map {
                    NewsArticle(
                        title = it.title,
                        url = it.url,
                        thumbnailUrl = it.urlToImage,
                        isBookmarked = false
                    )
                }

                val breakingNews = breakingArticles.map {
                    BreakingNews(
                        articleUrl = it.url
                    )
                }

                /**
                 * calling a database function in with transaction
                 * means all for one , one for all
                 * meaning if any one fails all should fail
                 * all should pass if all passed
                 * from this sample below -- All 3 functions must either pass
                 * or Fail
                 */

                db.withTransaction {
                    newsArticleDao.deleteAllBreakingNews()
                    newsArticleDao.insertArticles(breakingArticles)
                    newsArticleDao.insertBreakingNews(breakingNews)
                }

            }
        )
}

