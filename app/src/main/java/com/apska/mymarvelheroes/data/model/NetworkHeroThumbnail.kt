package com.apska.mymarvelheroes.data.model

data class NetworkHeroThumbnail(
    val path: String,
    val extension: String,
    val imageUrl: String = "$path.$extension"
)