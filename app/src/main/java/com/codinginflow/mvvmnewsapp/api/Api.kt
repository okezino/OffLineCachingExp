package com.codinginflow.mvvmnewsapp.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {

    companion object{
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "6c47db0cdd1246b2b69853c8ca035a06"
    }

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines?country=us&pageSize=100")
    suspend fun getBreakingNews() : ApiResponse

    @Headers("X-Api-Key: $API_KEY")
    @GET("everything")
    suspend fun  searchNews(
        @Query("q") query: String,
        @Query("page") page : Int,
        @Query("pageSize") pageSize : Int
    ) : ApiResponse
}