package com.alkalynx.githubusers.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alkalynx.githubusers.model.UsersModel

class FavoriteAdapter(private val activity: Activity) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    var listFavorites = ArrayList<UsersModel>()

    set(listFavorites){
        if(listFavorites.size > 0){
            this.listFavorites.clear()
        }
        this.listFavorites.addAll(listFavorites)
        notifyDataSetChanged()
    }

    fun addItem(usersModel: UsersModel){
        this.listFavorites.add(usersModel)
        notifyItemInserted(this.listFavorites.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {

    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

}