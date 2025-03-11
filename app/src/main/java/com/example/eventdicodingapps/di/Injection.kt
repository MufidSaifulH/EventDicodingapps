package com.example.eventdicodingapps.di

import android.content.Context
import com.example.eventdicodingapps.AppExecutors
import com.example.eventdicodingapps.EventRepository
import com.example.eventdicodingapps.data.local.room.DicodingDatabase
import com.example.eventdicodingapps.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = DicodingDatabase.getInstance(context)
        val dao = database.eventDao()
        val appExecutors = AppExecutors()
        return EventRepository.getInstance(apiService, dao, appExecutors)
    }
}