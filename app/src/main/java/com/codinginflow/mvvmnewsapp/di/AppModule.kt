package com.codinginflow.mvvmnewsapp.di

import android.app.Application
import androidx.room.Room
import com.codinginflow.mvvmnewsapp.api.Api
import com.codinginflow.mvvmnewsapp.data.NewsArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit{
      return   Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit) : Api{
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(app : Application) : NewsArticleDatabase{
  return Room.databaseBuilder(app,NewsArticleDatabase::class.java, "news_article_db")
      .fallbackToDestructiveMigration().build()
    }
}