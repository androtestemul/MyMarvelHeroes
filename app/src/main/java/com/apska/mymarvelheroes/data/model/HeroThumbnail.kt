package com.apska.mymarvelheroes.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HeroThumbnail(
    val path      : String,
    val extension : String,
    val imageUrl: String = "$path.$extension"
) : Parcelable