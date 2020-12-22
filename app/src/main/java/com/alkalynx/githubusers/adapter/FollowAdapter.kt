package com.alkalynx.githubusers.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alkalynx.githubusers.DetailActivity
import com.alkalynx.githubusers.R
import com.alkalynx.githubusers.model.UsersModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class FollowAdapter : RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    private var mData = ArrayList<UsersModel>()

    fun setData(data: ArrayList<UsersModel>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowAdapter.ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: UsersModel) {
            with(itemView) {
                Glide.with(this)
                    .load(user.avatarURL)
                    .into(user_avatar)
                username.text = user.login
                user_id.text = user.id.toString()
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                    this.putExtra(DetailActivity.EXTRA_USER, user)
                    setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                itemView.context.startActivity(intent)
            }
        }
    }

}