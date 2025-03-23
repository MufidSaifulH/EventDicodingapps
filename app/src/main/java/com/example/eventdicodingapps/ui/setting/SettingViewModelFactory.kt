package com.example.eventdicodingapps.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class SettingViewModelFactory (private val preference: SettingPreference) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(preference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}