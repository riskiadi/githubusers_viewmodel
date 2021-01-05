package com.alkalynx.githubusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.alkalynx.githubusers.databinding.ActivityDetailBinding
import com.alkalynx.githubusers.model.UsersModel
import com.alkalynx.githubusers.view_model.DetailViewModel
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private var mIsFavorite: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = applicationContext.resources.getString(R.string.detail_tittle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.elevation = 0f

        val user = intent.getParcelableExtra<UsersModel>(EXTRA_USER) as UsersModel
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager,user.login ?: "")

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        detailViewModel.initiate(this)

        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.username.text = user.login
        binding.userId.text = user.id.toString()
        Glide.with(this).load(user.avatarURL).into(binding.userAvatar)

        binding.favButton.setOnClickListener {
            val isFavorite :Boolean = mIsFavorite==1
            detailViewModel.setFavorite(isFavorite, UsersModel(user.login, user.id, user.avatarURL))
            if(mIsFavorite==0){
                mIsFavorite=1
                favoriteBtnState(true)
            }else{
                mIsFavorite=0
                favoriteBtnState(false)
            }
        }

        if(user.id!=null){
            detailViewModel.isFavorite(user.id).observe(this, { isFavorite ->
                mIsFavorite = isFavorite
                if (isFavorite <= 0) {
                    favoriteBtnState(false)
                } else {
                    favoriteBtnState(true)
                }
            })
        }


    }

    fun favoriteBtnState(state: Boolean){
        when(state){
            true -> binding.favButton.setImageResource(R.drawable.ic_baseline_favorite_24)
            else -> binding.favButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }

    }

}