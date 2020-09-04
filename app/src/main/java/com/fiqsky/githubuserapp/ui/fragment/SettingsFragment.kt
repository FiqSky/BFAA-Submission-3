package com.fiqsky.githubuserapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.fiqsky.githubuserapp.AlarmReceiver
import com.fiqsky.githubuserapp.R

class SettingsFragment : PreferenceFragmentCompat() {

    private val alarmReceiver = AlarmReceiver()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
//        val sharedPreferences = SettingPreference(context as Context).getInstance(context as Context)

        val dailyReminderSwitch = findPreference<SwitchPreferenceCompat>("notifications")
        val languagePreference = findPreference<Preference>("preference_language")

//        dailyReminderSwitch?.isChecked = sharedPreferences.checkDailyReminder() == true

        dailyReminderSwitch?.onPreferenceChangeListener = Preference.OnPreferenceChangeListener{ _, _ ->
            if (dailyReminderSwitch?.isChecked!!) {
                activity?.let { alarmReceiver.cancelAlarm(it) }
                val text = "alarm is disable"
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
                dailyReminderSwitch.isChecked = false
            } else {
                activity?.let { alarmReceiver.setRepeatAlarm(it) }
                val text = "alarm is enable"
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
                dailyReminderSwitch.isChecked = true
            }
            false
        }

        /*dailyReminderSwitch?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { preference, _ ->
                val dailySwitch = preference as SwitchPreferenceCompat

                if (dailySwitch.isChecked){
                    sharedPreferences.setDailyReminder(false)
                    alarmReceiver.cancelAlarm(context as Context, AlarmReceiver().DAILY)
                } else{
                    sharedPreferences.setDailyReminder(true)
                    alarmReceiver.setRepeatingAlarm(
                        context as Context,
                        "07:00",
                        getString(R.string.daily_notif_message)
                    )
                }
                true
            }*/

        languagePreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            true
        }
    }
}