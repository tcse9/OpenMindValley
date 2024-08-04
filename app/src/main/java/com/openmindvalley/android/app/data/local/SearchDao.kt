package com.openmindvalley.android.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchDao {
    @Query("SELECT * FROM searches")
    suspend fun getAllSearches(): List<SearchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(search: SearchEntity)
}