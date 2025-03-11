package com.example.eventdicodingapps.ui.setting

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eventdicodingapps.EventRepository
import com.example.eventdicodingapps.MainViewModel
import com.example.eventdicodingapps.di.Injection

class SettingViewModelFactory (private val preference: SettingPreference) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(preference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

//    companion object {
//        @Volatile
//        private var instance: SettingViewModelFactory? = null
//        fun getInstance(context: Context): SettingViewModelFactory =
//            instance ?: synchronized(this) {
//                instance ?: SettingViewModelFactory(Injection.provideRepository(context))
//            }.also { instance = it }
//    }
}