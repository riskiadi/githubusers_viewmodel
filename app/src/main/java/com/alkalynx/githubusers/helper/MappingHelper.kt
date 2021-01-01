package com.alkalynx.githubusers.helper

import android.database.Cursor
import com.alkalynx.githubusers.db.DatabaseContract
import com.alkalynx.githubusers.db.FavoriteHelper
import com.alkalynx.githubusers.model.FavoritesModel

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<FavoritesModel> {
        val favoritesUser = ArrayList<FavoritesModel>()

        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns._ID))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.USERNAME))
                val userId = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.USER_ID))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR))
                val isFavorite = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.IS_FAVORITE))
                favoritesUser.add(FavoritesModel(id, username, userId.toLong(), avatar, isFavorite.toInt() ))
            }
        }
        return favoritesUser
    }
}