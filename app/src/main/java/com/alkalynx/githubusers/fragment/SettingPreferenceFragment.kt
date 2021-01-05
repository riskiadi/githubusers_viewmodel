package com.alkalynx.githubusers.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.alkalynx.githubusers.AlarmReceiver
import com.alkalynx.githubusers.R


class SettingPreference: PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var ACTIVE: String
    private  lateinit var activeReminderPreference: SwitchPreference

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var mContext : Context

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        initial()
        setSummaries()
    }

    private fun initial(){
        ACTIVE = resources.getString(R.string.key_active)
        activeReminderPreference = findPreference<SwitchPreference>(ACTIVE) as SwitchPreference
        alarmReceiver = AlarmReceiver()
    }

    private fun setSummaries(){
        val sh = preferenceManager.sharedPreferences
        val isChecked :Boolean = sh.getBoolean(ACTIVE, false)
        activeReminderPreference.isChecked = isChecked
        activeReminderPreference.summary = if(isChecked) resources.getString(R.string.active_summary) else resources.getString(R.string.disable_summary)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if(key == ACTIVE){
            val isChecked :Boolean = sharedPreferences.getBoolean(ACTIVE, false)
            activeReminderPreference.isChecked = isChecked
            activeReminderPreference.summary = if(isChecked) resources.getString(R.string.active_summary) else resources.getString(R.string.disable_summary)
            if(isChecked) {
                alarmReceiver.setRepeatingAlarm(mContext,"09:00", "Lets find popular user on github")
            }else{
                alarmReceiver.cancelAlarm(mContext)
            }
        }
    }

}