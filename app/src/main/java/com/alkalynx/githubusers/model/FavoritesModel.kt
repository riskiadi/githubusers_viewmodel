package com.alkalynx.githubusers.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoritesModel(
    val dbId : Int? = null,
    val login: String,
    val id: Long? = null,
    val avatarURL: String? = null,
    val isFavorites: Int? = null
):Parcelable