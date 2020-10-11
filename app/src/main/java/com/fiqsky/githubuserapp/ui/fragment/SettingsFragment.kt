package com.fiqsky.githubuserapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.fiqsky.githubuserapp.ui.receiver.AlarmReceiver
import com.fiqsky.githubuserapp.R

class SettingsFragment : PreferenceFragmentCompat() {

    private val alarmReceiver = AlarmReceiver()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)

        val dailyReminderSwitch = findPreference<SwitchPreferenceCompat>("notifications")
        val languagePreference = findPreference<Preference>("preference_language")

        dailyReminderSwitch?.onPreferenceChangeListener = Preference.OnPreferenceChangeListener{ _, _ ->
            if (dailyReminderSwitch?.isChecked!!) {
                activity?.let { alarmReceiver.cancelAlarm(it) }
                val text = R.string.notification_disable
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
                dailyReminderSwitch.isChecked = false
            } else {
                activity?.let { alarmReceiver.setRepeatAlarm(it) }
                val text = R.string.notification_enable
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
                dailyReminderSwitch.isChecked = true
            }
            false
        }

        languagePreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            true
        }
    }
}