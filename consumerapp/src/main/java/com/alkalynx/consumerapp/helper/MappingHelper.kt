package com.alkalynx.consumerapp.helper

import android.database.Cursor
import com.alkalynx.consumerapp.db.DatabaseContract
import com.alkalynx.consumerapp.model.UsersModel

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<UsersModel> {
        val favoritesUser = ArrayList<UsersModel>()

        notesCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.USERNAME))
                val userId = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.USER_ID))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR))
                favoritesUser.add(UsersModel(username, userId.toLong(), avatar))
            }
        }
        return favoritesUser
    }

}