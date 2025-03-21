package com.example.eventdicodingapps.data.remote.retrofit

import com.example.eventdicodingapps.data.remote.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getEvents(
        @Query("active") active: Int = 1,
        @Query("q") query: String? = null,
        @Query("limit") limit: Int = 40,
    ): EventResponse

    @GET("events")
    suspend fun getUpcomingEvent(
        @Query("active") active: Int = -1,  // -1 untuk event aktif terdekat
        @Query("limit") limit: Int = 1      // Ambil hanya 1 event
    ): EventResponse
}