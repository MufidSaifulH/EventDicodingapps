package com.example.eventdicodingapps

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.eventdicodingapps.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class DailyReminderWorker (context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        return runBlocking(Dispatchers.IO) {
            try {
                val apiService = ApiConfig.getApiService()
                val response = apiService.getUpcomingEvent()
                val event = response.listEvents?.firstOrNull{it.active == 1}

               if (event != null) {
                   NotificationHelper.showNotification(
                       applicationContext,
                       "Upcoming Event: ${event.name}",
                       "Event Date: ${event.beginTime}"
                   )
               }
                Result.success()
            } catch (e: Exception) {
                Log.e("DailyReminderWorker", "Error fetching event: ${e.message}")
                Result.retry()
            }
        }
    }
}