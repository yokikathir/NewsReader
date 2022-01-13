package com.kathir.newsreader.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by KATHIR on 12-01-2022.
 */
interface ApiInterface {

    @get:GET("v0/topstories.json?print=pretty")
    val topStories: Call<List<Int?>?>?

    @GET("v0/item/{articleid}.json?print=pretty")
    fun getArticle(@Path("articleid") id: Int): Call<ArticleResponse?>?

}