package com.fachrul.api_service.service

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    fun getClient(): Retrofit {
        return Retrofit.Builder()
            .client(
                OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor() {
                    Log.e("InetLog", it)
                }).build()
            )
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }
}