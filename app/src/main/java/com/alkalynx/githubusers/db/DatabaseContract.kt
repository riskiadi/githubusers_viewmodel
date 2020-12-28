package com.alkalynx.githubusers.db

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "users"
            const val _ID = "_id"
            const val USERNAME = "github_username"
            const val USER_ID = "github_id"
            const val AVATAR = "github_avatar"
            const val IS_FAVORITE = "is_favorite"
        }
    }

}