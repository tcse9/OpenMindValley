package com.openmindvalley.android.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SearchEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao
}