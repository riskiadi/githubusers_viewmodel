package com.alkalynx.githubusers.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alkalynx.githubusers.DetailActivity
import com.alkalynx.githubusers.R
import com.alkalynx.githubusers.databinding.RecyclerviewItemBinding
import com.alkalynx.githubusers.model.UsersModel
import com.bumptech.glide.Glide

class FavoriteAdapter(private val activity: Activity) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    var listFavorites = ArrayList<UsersModel>()

    set(listFavorites){
        if(listFavorites.size > 0){
            this.listFavorites.clear()
        }
        this.listFavorites.addAll(listFavorites)
        notifyDataSetChanged()
    }

    fun addItem(favoritesModel: UsersModel){
        this.listFavorites.add(favoritesModel)
        notifyItemInserted(this.listFavorites.size - 1)
    }

    fun updateItem(position: Int, favoritesModel: UsersModel){
        this.listFavorites[position] = favoritesModel
        notifyItemChanged(position, favoritesModel)
    }

    fun removeItem(position: Int){
        this.listFavorites.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listFavorites.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }

    override fun getItemCount(): Int = this.listFavorites.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = RecyclerviewItemBinding.bind(itemView)
        fun bind(user: UsersModel){
            with(itemView) {
                Glide.with(this)
                    .load(user.avatarURL)
                    .into(binding.userAvatar)
                binding.username.text = user.login
                binding.userId.text = user.id.toString()
            }
            itemView.setOnClickListener {
                val intent = Intent(activity, DetailActivity::class.java).apply {
                    this.putExtra(DetailActivity.EXTRA_USER, user)
                    setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                itemView.context.startActivity(intent)
            }

        }
    }

}