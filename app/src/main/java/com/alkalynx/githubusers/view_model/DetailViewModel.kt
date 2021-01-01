package com.alkalynx.githubusers.view_model

import android.content.Context
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkalynx.githubusers.db.FavoriteHelper
import com.alkalynx.githubusers.helper.MappingHelper
import com.alkalynx.githubusers.model.UsersModel
import com.alkalynx.githubusers.utils.Constant
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.lang.Exception

class DetailViewModel : ViewModel() {

    val const: Constant = Constant()

    private val mData = MutableLiveData<ArrayList<UsersModel>>()
    private val mIsFavorite = MutableLiveData<Int>()
    private lateinit var favoriteHelper: FavoriteHelper

    fun searchFollower(username: String) {
        try {
            val userItem = ArrayList<UsersModel>()
            AndroidNetworking.get(const.followerURL)
                .addPathParameter("username", username)
                .addHeaders("Authorization", "token ${const.token}")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(object : JSONArrayRequestListener {
                    override fun onResponse(response: JSONArray) {

                        for (i in 0 until response.length()) {
                            var json = response.getJSONObject(i)
                            val userData = UsersModel(
                                json.getString("login"),
                                json.getLong("id"),
                                json.getString("node_id"),
                                json.getString("avatar_url"),
                                json.getString("url"),
                                json.getString("followers_url"),
                                json.getString("following_url"),
                            )
                            userItem.add(userData)
                        }
                        mData.postValue(userItem)
                    }

                    override fun onError(anError: ANError?) {
                        Log.e("api", anError.toString())
                    }

                })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun searchFollowing(username: String) {
        try {
            val userItem = ArrayList<UsersModel>()
            AndroidNetworking.get(const.followingURL)
                .addPathParameter("username", username)
                .addHeaders("Authorization", "token ${const.token}")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(object : JSONArrayRequestListener {
                    override fun onResponse(response: JSONArray) {

                        for (i in 0 until response.length()) {
                            var json = response.getJSONObject(i)
                            val userData = UsersModel(
                                json.getString("login"),
                                json.getLong("id"),
                                json.getString("node_id"),
                                json.getString("avatar_url"),
                                json.getString("url"),
                                json.getString("followers_url"),
                                json.getString("following_url"),
                            )
                            userItem.add(userData)
                        }
                        mData.postValue(userItem)
                    }

                    override fun onError(anError: ANError?) {
                        Log.e("api", anError.toString())
                    }

                })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getUser(): LiveData<ArrayList<UsersModel>> {
        return mData
    }

    fun isFavorite(context: Context, userId: Long): LiveData<Int>{
        favoriteHelper = FavoriteHelper.getInstance(context)
        favoriteHelper.open()
        loadDatabaseAsync(favoriteHelper.queryById(userId.toString()))
        return mIsFavorite
    }

    private fun loadDatabaseAsync(cursorData: Cursor){
        GlobalScope.launch(Dispatchers.Main) {
            val temp = async(Dispatchers.IO) {
                MappingHelper.mapCursorToArrayList(cursorData)
            }
            val favorite = temp.await()
            if(favorite.size>0){
                mIsFavorite.postValue(favorite.first().isFavorites)
            }
        }
    }

}