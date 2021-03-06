package com.apska.mymarvelheroes.data.model

data class HeroResponse (
    val code            : Int?    = null,
    val status          : String? = null,
    val copyright       : String? = null,
    val attributionText : String? = null,
    val attributionHTML : String? = null,
    val etag            : String? = null,
    val data            : HeroListData
)