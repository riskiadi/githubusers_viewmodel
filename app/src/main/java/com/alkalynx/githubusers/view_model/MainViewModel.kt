package com.alkalynx.githubusers.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkalynx.githubusers.model.UsersModel
import com.alkalynx.githubusers.utils.Constant
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {

    private val listUser = MutableLiveData<ArrayList<UsersModel>>()
    private val searchText = MutableLiveData<String>()
    val const: Constant = Constant()

    fun setSearchText(text: String) {
        searchText.postValue(text)
    }

    fun searchUser() {
        try {
            val userItem = ArrayList<UsersModel>()
            AndroidNetworking.get(const.searchURL)
                .addPathParameter("username", searchText.value)
                .addHeaders("Authorization", "token ${const.token}").setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        for (i in 0 until response.getJSONArray("items").length()) {
                            var json = response.getJSONArray("items").getJSONObject(i)
                            val userData = UsersModel(
                                json.getString("login"),
                                json.getLong("id"),
                                json.getString("avatar_url")
                            )
                            userItem.add(userData)
                        }

                        listUser.postValue(userItem)

                    }

                    override fun onError(anError: ANError?) {
                        Log.e("API", anError.toString())
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getUsers(): LiveData<ArrayList<UsersModel>> {
        return listUser
    }

    fun getSearchText(): LiveData<String> {
        return searchText
    }

}