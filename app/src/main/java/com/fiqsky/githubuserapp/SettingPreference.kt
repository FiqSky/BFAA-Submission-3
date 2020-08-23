package com.fiqsky.githubuserapp

import android.content.Context
import android.content.SharedPreferences

class SettingPreference(val context: Context) {

    private val s = "github_user"
    private val init = "init"
    private val daily = "daily_reminder"

    private var mInstance : SettingPreference? = null
    private val sharedPreferences : SharedPreferences? = context.getSharedPreferences(s, Context.MODE_PRIVATE)

    @Synchronized
    fun getInstance(context: Context): SettingPreference {
        if (mInstance == null) {
            mInstance = SettingPreference(context)
        }
        return mInstance as SettingPreference
    }

    fun setDailyReminder(boolean: Boolean){
        val editor = sharedPreferences?.edit()

        editor?.putBoolean(daily, boolean)
        editor?.putInt(init,1)
        editor?.apply()
    }

    fun checkDailyReminder(): Boolean? {
        return sharedPreferences?.getBoolean(daily, true)
    }

    fun checkInit(): Int? {
        return sharedPreferences?.getInt(init,0)
    }
}