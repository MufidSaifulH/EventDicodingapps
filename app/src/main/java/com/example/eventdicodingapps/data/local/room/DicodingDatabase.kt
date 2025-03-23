package com.example.eventdicodingapps.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.eventdicodingapps.data.local.entity.EventEntity

@Database(entities = [EventEntity::class], version = 2, exportSchema = false)
abstract class DicodingDatabase : RoomDatabase() {
    abstract fun eventDao() : DicodingDao

    companion object {
        @Volatile
        private var instance: DicodingDatabase? = null
        fun getInstance(context: Context): DicodingDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    DicodingDatabase::class.java, "Event.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
    }
}