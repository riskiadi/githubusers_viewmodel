package com.alkalynx.githubusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkalynx.githubusers.R
import com.alkalynx.githubusers.adapter.FavoriteAdapter
import com.alkalynx.githubusers.databinding.ActivityFavoriteBinding
import com.alkalynx.githubusers.view_model.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    lateinit var binding : ActivityFavoriteBinding
    lateinit var favoriteAdapter : FavoriteAdapter
    lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = applicationContext.resources.getString(R.string.favorites)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.elevation = 0f

        favoriteAdapter = FavoriteAdapter(this)
        favoriteViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FavoriteViewModel::class.java)

        binding.favRecycler.setHasFixedSize(true)
        binding.favRecycler.layoutManager = LinearLayoutManager(applicationContext)
        binding.favRecycler.adapter = favoriteAdapter

        favoriteViewModel.getFavorite(applicationContext).observe(this, {
            if(it!=null){
                favoriteAdapter.listFavorites = it
            }
        })



    }
}