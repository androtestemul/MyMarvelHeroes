package com.apska.mymarvelheroes.data.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HeroApi {
    private const val BASE_URL = "https://gateway.marvel.com/"
    const val PUBLIC_API_KEY = "7e4785fa72e879fd5a8ff81bcbbe0ef2"
    const val LIMIT = 30

    private fun getRetrofit() : Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        //httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .build()

    }

    val heroApiService: HeroApiService by lazy {
        getRetrofit().create(HeroApiService::class.java)
    }
}
