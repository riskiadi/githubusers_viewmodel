package com.alkalynx.githubusers.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.alkalynx.githubusers.db.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.alkalynx.githubusers.db.DatabaseContract.UserColumns.Companion.USER_ID

class FavoriteHelper(context: Context) {


    init {
        databaseHelper = DatabaseHelper(context)
    }

    companion object{
        private const val DATABASE_TABLE = TABLE_NAME
        private val INSTANCE: FavoriteHelper? = null
        private lateinit var databaseHelper: DatabaseHelper
        private lateinit var database: SQLiteDatabase
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteHelper(context)
            }

    }

    @Throws(SQLException::class)
    fun open(){
        database = databaseHelper.writableDatabase
    }

    fun close(){
        databaseHelper.close()
        if (database.isOpen) database.close()
    }

    fun queryAll():Cursor{
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$USER_ID ASC"
        )
    }

    fun queryById(id: String): Cursor{
        return database.query(
            DATABASE_TABLE,
            null,
            "$USER_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    fun insert(values: ContentValues?): Long{
        return database.insert(
            DATABASE_TABLE,
            null,
            values
        )
    }

    fun insertWithOnConflict(values: ContentValues?): Long{
        return database.insertWithOnConflict(
            DATABASE_TABLE,
            null,
            values,
            SQLiteDatabase.CONFLICT_IGNORE
        )
    }

    fun update(id: String, values: ContentValues?): Int{
        return database.update(
            DATABASE_TABLE,
            values,
            "$USER_ID = ?",
            arrayOf(id),
        )
    }

    fun delete(id: String): Int{
        return database.delete(
            DATABASE_TABLE,
            "$USER_ID = '$id'",
            null
        )
    }

}