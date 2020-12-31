package com.alkalynx.githubusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alkalynx.githubusers.fragment.SettingPreference

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.title = resources.getString(R.string.settings)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        supportFragmentManager.beginTransaction().add(R.id.setting_holder, SettingPreference()).commit()

    }
}