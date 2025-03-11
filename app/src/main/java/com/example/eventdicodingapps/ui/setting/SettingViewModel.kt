package com.example.eventdicodingapps.ui.setting

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

class SettingViewModel(private val pref : SettingPreference) : ViewModel() {
    fun getThemeSetting(): Flow<Boolean> = pref.getThemeSetting()

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        pref.saveThemeSetting(isDarkModeActive)
    }
}