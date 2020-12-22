package com.alkalynx.githubusers.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UsersModel (
    val login: String,
    val id: Long? = null,
    val nodeID: String? = null,
    val avatarURL: String? = null,
    val url: String? = null,
    val followersURL: String? = null,
    val followingURL: String? = null,
):Parcelable
