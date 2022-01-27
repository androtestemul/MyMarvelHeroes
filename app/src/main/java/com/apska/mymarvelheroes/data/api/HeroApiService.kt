package com.apska.mymarvelheroes.data.api

import com.apska.mymarvelheroes.BuildConfig
import com.apska.mymarvelheroes.data.model.HeroResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface HeroApiService {
    @GET("/v1/public/characters?limit=${HeroApi.LIMIT}&apikey=${HeroApi.PUBLIC_API_KEY}")
    fun getHeroesList(@Query("offset") offset: Long, @Query("ts") ts: Long, @Query("hash") hash: String): Deferred<HeroResponse>
}