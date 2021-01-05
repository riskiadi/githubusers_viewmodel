package com.alkalynx.consumerapp

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkalynx.consumerapp.adapter.FavoriteAdapter
import com.alkalynx.consumerapp.databinding.ActivityMainBinding
import com.alkalynx.consumerapp.db.DatabaseContract
import com.alkalynx.consumerapp.helper.MappingHelper
import com.alkalynx.consumerapp.model.UsersModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object{
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteAdapter = FavoriteAdapter(this)
        binding.recyclerUsers.setHasFixedSize(true)
        binding.recyclerUsers.layoutManager = LinearLayoutManager(this)
        binding.recyclerUsers.adapter = favoriteAdapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadFavorite()
            }
        }

        contentResolver.registerContentObserver(DatabaseContract.CONTENT_URI, true, myObserver)

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
                Toast.makeText(this@MainActivity, R.string.data_not_available, Toast.LENGTH_SHORT).show()
            }
        }
    }

}