package com.fiqsky.githubuserapp.ui.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.fiqsky.githubuserapp.AlarmReceiver
import com.fiqsky.githubuserapp.R
import com.fiqsky.githubuserapp.SettingPreference

class SettingsFragment : PreferenceFragmentCompat() {

    private val alarmReceiver : AlarmReceiver()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
        val sharedPreferences = SettingPreference(context as Context).getInstance(context as Context)

        val dailyReminderSwitch = findPreference<SwitchPreferenceCompat>("notifications")
        val languagePreference = findPreference<Preference>("preference_language")

        dailyReminderSwitch?.isChecked = sharedPreferences.checkDailyReminder() == true



        languagePreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            true
        }
    }
}