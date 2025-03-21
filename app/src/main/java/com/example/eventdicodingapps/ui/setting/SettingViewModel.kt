package com.example.eventdicodingapps.ui.setting

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

class SettingViewModel(private val pref : SettingPreference) : ViewModel() {
    fun getThemeSetting(): Flow<Boolean> = pref.getThemeSetting()
    fun getNotificationSetting(): Flow<Boolean> = pref.getNotificationSetting()

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        pref.saveThemeSetting(isDarkModeActive)
    }
    suspend fun saveNotificationSetting(isNotificationActive: Boolean) {
        pref.saveNotificationSetting(isNotificationActive)
    }

}