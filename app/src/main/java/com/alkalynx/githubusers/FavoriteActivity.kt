package com.alkalynx.githubusers

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkalynx.githubusers.adapter.FavoriteAdapter
import com.alkalynx.githubusers.databinding.ActivityFavoriteBinding
import com.alkalynx.githubusers.db.DatabaseContract
import com.alkalynx.githubusers.db.DatabaseContract.CONTENT_URI
import com.alkalynx.githubusers.helper.MappingHelper
import com.alkalynx.githubusers.model.UsersModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    companion object{
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    lateinit var binding : ActivityFavoriteBinding
    lateinit var favoriteAdapter : FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = applicationContext.resources.getString(R.string.favorites)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.elevation = 0f

        favoriteAdapter = FavoriteAdapter(this)

        binding.favRecycler.setHasFixedSize(true)
        binding.favRecycler.layoutManager = LinearLayoutManager(applicationContext)
        binding.favRecycler.adapter = favoriteAdapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadFavorite()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadFavorite()
        } else {
            savedInstanceState.getParcelableArrayList<UsersModel>(EXTRA_STATE)?.also {
                favoriteAdapter.listFavorites = it
            }
        }

    }

    private fun loadFavorite(){
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver.query(DatabaseContract.CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favorite : ArrayList<UsersModel> = deferredNotes.await()
            if (favorite.size > 0) {
                favoriteAdapter.listFavorites = favorite
            }else{
                favoriteAdapter.listFavorites = ArrayList()
                Toast.makeText(this@FavoriteActivity, R.string.data_not_available, Toast.LENGTH_SHORT).show()
            }
        }
    }

}