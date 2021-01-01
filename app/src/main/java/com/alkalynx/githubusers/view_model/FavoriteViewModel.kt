package com.alkalynx.githubusers.view_model

import android.content.Context
import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkalynx.githubusers.db.FavoriteHelper
import com.alkalynx.githubusers.helper.MappingHelper
import com.alkalynx.githubusers.model.FavoritesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class FavoriteViewModel: ViewModel() {

    private val users = MutableLiveData<ArrayList<FavoritesModel>>()
    private lateinit var database: FavoriteHelper

    fun getFavorite(context: Context): LiveData<ArrayList<FavoritesModel>> {
        database = FavoriteHelper.getInstance(context)
        database.open()
        loadDatabaseAsync(database.queryAll())
        return users
    }

    private fun loadDatabaseAsync(cursorData: Cursor){
        GlobalScope.launch(Dispatchers.Main) {
            val temp = async(Dispatchers.IO) {
                val cursor = cursorData
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favorite = temp.await()
            if(favorite.size>0){
                users.postValue(favorite)
            }
        }
    }

}