package com.example.eventdicodingapps

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eventdicodingapps.databinding.ActivityMainBinding
import com.example.eventdicodingapps.ui.setting.SettingPreference
import com.example.eventdicodingapps.ui.setting.SettingViewModel
import com.example.eventdicodingapps.ui.setting.SettingViewModelFactory
import com.example.eventdicodingapps.ui.setting.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var settingViewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi SettingPreference
        val pref = SettingPreference.getInstance(dataStore) // Gunakan dataStore langsung
        val factory = SettingViewModelFactory(pref)
        settingViewModel = ViewModelProvider(this, factory).get(SettingViewModel::class.java)

        // Observasi perubahan tema
        settingViewModel.getThemeSetting().asLiveData().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val bottomNav: BottomNavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNav.setupWithNavController(navController)
    }
}