package com.alkalynx.githubusers.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UsersModel(
    val login: String? = null,
    val id: Long? = null,
    val avatarURL: String? = null,
) : Parcelable
