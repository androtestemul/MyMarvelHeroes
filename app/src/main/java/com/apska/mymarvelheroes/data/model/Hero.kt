package com.apska.mymarvelheroes.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hero(
    val id          : Int,
    val name        : String,
    val description : String,
    val thumbnail   : HeroThumbnail
) : Parcelable
