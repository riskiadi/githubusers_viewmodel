package com.alkalynx.githubusers.adapter

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

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val mData = ArrayList<UsersModel>()

    fun setData(list: ArrayList<UsersModel>) {
        mData.clear()
        mData.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RecyclerviewItemBinding.bind(itemView)
        fun bind(user: UsersModel) {
            Glide.with(itemView.context)
                .load(user.avatarURL)
                .into(binding.userAvatar)
            binding.username.text = user.login
            binding.userId.text = user.id.toString()
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                    this.putExtra(DetailActivity.EXTRA_USER, user)
                }
                itemView.context.startActivity(intent)
            }
        }
    }

}