package com.alkalynx.githubusers.db

import android.net.Uri
import android.provider.BaseColumns
import com.alkalynx.githubusers.db.DatabaseContract.UserColumns.Companion.TABLE_NAME

object DatabaseContract {

    const val AUTHORITY = "com.alkalynx.githubusers"
    const val SCHEME = "content"

    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorites"
            const val USER_ID = "github_id"
            const val USERNAME = "github_username"
            const val AVATAR = "github_avatar"
        }
    }

    val CONTENT_URI : Uri = Uri.Builder().scheme(SCHEME).authority(AUTHORITY).appendPath(TABLE_NAME).build()


}