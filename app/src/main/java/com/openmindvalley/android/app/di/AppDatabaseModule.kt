package com.openmindvalley.android.app.di

import android.content.Context
import androidx.room.Room
import com.openmindvalley.android.app.data.local.AppDatabase
import com.openmindvalley.android.app.data.local.SearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideSearchDao(database: AppDatabase): SearchDao {
        return database.searchDao()
    }
}