package com.apska.mymarvelheroes.data.model

data class NetworkHero(
    val id          : Int,
    val name        : String,
    val description : String,
    val thumbnail   : NetworkHeroThumbnail
)