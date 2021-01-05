package com.alkalynx.githubusers.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.alkalynx.githubusers.db.DatabaseContract.UserColumns
import com.alkalynx.githubusers.db.DatabaseContract.UserColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbgithub"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_GITHUB = "CREATE TABLE $TABLE_NAME" +
                " (${UserColumns.USER_ID} INTEGER PRIMARY KEY," +
                " ${UserColumns.USERNAME} TEXT NOT NULL," +
                " ${UserColumns.AVATAR} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_GITHUB)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}