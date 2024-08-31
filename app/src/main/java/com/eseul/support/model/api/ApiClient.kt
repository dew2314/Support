package com.eseul.support.model.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    @JvmStatic
    val retrofit: Retrofit
        get() {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BASIC
                        }).build()
                )
                .baseUrl("http://43.203.31.200:8080")
                .build()
        }
}