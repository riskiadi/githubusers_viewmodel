package com.alkalynx.githubusers

import android.app.SearchManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkalynx.githubusers.adapter.MainAdapter
import com.alkalynx.githubusers.databinding.ActivityMainBinding
import com.alkalynx.githubusers.db.DatabaseContract
import com.alkalynx.githubusers.db.FavoriteHelper
import com.alkalynx.githubusers.view_model.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainAdapter: MainAdapter
    private lateinit var alarmReceiver: AlarmReceiver

    private lateinit var favoriteHelper: FavoriteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainAdapter = MainAdapter()

        binding.recyclerUsers.setHasFixedSize(true)
        binding.recyclerUsers.layoutManager = LinearLayoutManager(this)
        binding.recyclerUsers.adapter = mainAdapter

        alarmReceiver = AlarmReceiver()

//        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
//        favoriteHelper.open()
//        val values  = ContentValues()
//        values.put(DatabaseContract.UserColumns.USERNAME, "tolitoli")
//        values.put(DatabaseContract.UserColumns.USER_ID, "10222")
//        values.put(DatabaseContract.UserColumns.AVATAR, "monkey.jpg")
//        values.put(DatabaseContract.UserColumns.IS_FAVORITE, 0)
//        favoriteHelper.insert(values)

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        showLoading(true)
        mainViewModel.searchUser()
        mainViewModel.getSearchText().observe(this, {
            if (it != null) {
                supportActionBar?.title = it
                mainViewModel.searchUser()
            }
        })
        mainViewModel.getUsers().observe(this, {
            if (it != null) {
                mainAdapter.setData(it)
                showLoading(false)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_menu).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    showLoading(false)
                } else {
                    showLoading(true)
                    mainViewModel.searchUser()
                    mainViewModel.setSearchText(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    showLoading(false)
                } else {
                    showLoading(true)
                    mainViewModel.searchUser()
                    mainViewModel.setSearchText(newText)
                }
                return false
            }

        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.fav_menu ->{
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.setting_menu->{
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerUsers.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.recyclerUsers.visibility = View.VISIBLE
        }
    }

}