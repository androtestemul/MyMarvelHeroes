package com.apska.mymarvelheroes.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HeroThumbnail(
    val path      : String,
    val extension : String
) : Parcelable {
    val imageUrl: String
        get() = path + "." + extension
}