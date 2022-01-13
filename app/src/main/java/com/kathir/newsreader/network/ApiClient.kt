package com.kathir.newsreader.network

import android.content.Context
import retrofit2.Retrofit
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by KATHIR on 12-01-2022.
 */
object ApiClient {

    const val Base_URL = "https://hacker-news.firebaseio.com/"

    private var retrofit: Retrofit? = null

    fun getClient(context: Context?): Retrofit? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        if (retrofit == null) {
            val ok = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            retrofit = Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    ok.newBuilder().connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
                        .build()
                )
                .build()
        }
        return retrofit
    }

}