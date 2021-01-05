package com.alkalynx.githubusers.view_model

import android.app.Activity
import android.content.ContentValues
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkalynx.githubusers.db.DatabaseContract
import com.alkalynx.githubusers.db.DatabaseContract.CONTENT_URI
import com.alkalynx.githubusers.model.UsersModel
import com.alkalynx.githubusers.utils.Constant
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import org.json.JSONArray
import java.lang.Exception

class DetailViewModel : ViewModel() {

    val const: Constant = Constant()
    private var mActivity: Activity? = null

    private val mData = MutableLiveData<ArrayList<UsersModel>>()
    private val mIsFavorite = MutableLiveData<Int>()

    fun initiate(activity: Activity){
        this.mActivity = activity
    }

    fun searchFollower(username: String) {
        try {
            val userItem = ArrayList<UsersModel>()
            AndroidNetworking.get(const.followerURL)
                .addPathParameter("username", username)
                .addHeaders("Authorization", "token ${const.token}")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONArray(object : JSONArrayRequestListener {
                    override fun onResponse(response: JSONArray) {

                        for (i in 0 until response.length()) {
                            var json = response.getJSONObject(i)
                            val userData = UsersModel(
                                json.getString("login"),
                                json.getLong("id"),
                                json.getString("avatar_url"),
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
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONArray(object : JSONArrayRequestListener {
                    override fun onResponse(response: JSONArray) {

                        for (i in 0 until response.length()) {
                            var json = response.getJSONObject(i)
                            val userData = UsersModel(
                                json.getString("login"),
                                json.getLong("id"),
                                json.getString("avatar_url")
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

    fun isFavorite(userId: Long): LiveData<Int> {
        val uriWithId: Uri = Uri.parse(CONTENT_URI.toString() + "/" + userId.toString())
        val dbCursor: Cursor? = mActivity?.contentResolver?.query(uriWithId, null, null, null, null)
        if (dbCursor != null && dbCursor.count >= 1) {
            mIsFavorite.postValue(1)
        } else {
            mIsFavorite.postValue(0)
        }
        return mIsFavorite
    }

    fun setFavorite(favorite: Boolean, favoritesModel: UsersModel){

        if(favoritesModel.id!=null){
            if(favorite){
                val uri:Uri = Uri.parse(CONTENT_URI.toString()+"/"+favoritesModel.id)
                mActivity?.contentResolver?.delete(uri, null,null)
            }else{
                val values = ContentValues()
                values.put(DatabaseContract.UserColumns.USERNAME, favoritesModel.login)
                values.put(DatabaseContract.UserColumns.USER_ID, favoritesModel.id)
                values.put(DatabaseContract.UserColumns.AVATAR, favoritesModel.avatarURL)
                val uriWithId : Uri = Uri.parse(CONTENT_URI.toString() + "/" + favoritesModel.id.toString())
                mActivity?.contentResolver?.insert(uriWithId, values)
            }
        }

    }

}