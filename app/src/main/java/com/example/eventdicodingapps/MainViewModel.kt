package com.example.eventdicodingapps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventdicodingapps.data.local.entity.EventEntity
import kotlinx.coroutines.launch


class MainViewModel(private val eventRepository: EventRepository) : ViewModel() {

    fun getUpcomingEvents() = eventRepository.getUpcomingEvents()
    fun getFinishedEvents() = eventRepository.getFinishedEvents()
    fun getFavoriteEvents() = eventRepository.getFavoriteEvents()


    fun searchFinishedEvents(query: String): LiveData<Result<List<EventEntity>>> =
        eventRepository.searchFinishedEvents(query)

    fun searchFavoriteEvents(query: String): LiveData<Result<List<EventEntity>>> =
        eventRepository.searchFavoriteEvents(query)

    fun saveEvents(events: EventEntity) {
        viewModelScope.launch {
            eventRepository.setFavoriteEvents(events, true)
        }
    }

    fun deleteEvents(events: EventEntity) {
        viewModelScope.launch {
            eventRepository.setFavoriteEvents(events, false)
        }
    }

}