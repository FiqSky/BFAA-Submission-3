package com.fiqsky.githubuserapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fiqsky.githubuserapp.R
import com.fiqsky.githubuserapp.ui.fragment.SettingsFragment

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        title = getString(R.string.setting)

        loadFragment(fragment = SettingsFragment())
    }

    private fun loadFragment(fragment: SettingsFragment):Boolean {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_settings, fragment)
            .commit()
        return true
    }
}