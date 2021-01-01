package com.alkalynx.githubusers

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.alkalynx.githubusers.databinding.ActivityDetailBinding
import com.alkalynx.githubusers.db.DatabaseContract
import com.alkalynx.githubusers.db.FavoriteHelper
import com.alkalynx.githubusers.model.UsersModel
import com.alkalynx.githubusers.view_model.DetailViewModel
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favoriteHelper: FavoriteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = applicationContext.resources.getString(R.string.detail_tittle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.elevation = 0f

        favoriteHelper = FavoriteHelper.getInstance(this)
        val user = intent.getParcelableExtra<UsersModel>(EXTRA_USER) as UsersModel
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, user.login)

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.username.text = user.login
        binding.userId.text = user.id.toString()
        Glide.with(this).load(user.avatarURL).into(binding.userAvatar)
        binding.favButton.setOnClickListener {
            favoriteHelper.open()
            val values = ContentValues()
            values.put(DatabaseContract.UserColumns.USERNAME, user.login)
            values.put(DatabaseContract.UserColumns.USER_ID, user.id)
            values.put(DatabaseContract.UserColumns.AVATAR, user.avatarURL)
            values.put(DatabaseContract.UserColumns.IS_FAVORITE, 0)
            favoriteHelper.insert(values)
            favoriteHelper.close()
        }

        if(user.id!=null){
            detailViewModel.isFavorite(this, user.id).observe(this, {isFavorite->
                Log.d("ddbug", isFavorite.toString())
                if(isFavorite<=0){
                    binding.favButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }else{
                    binding.favButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                }
            })
        }



    }

}