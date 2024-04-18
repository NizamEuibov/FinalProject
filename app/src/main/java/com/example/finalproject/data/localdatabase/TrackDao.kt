package com.example.finalproject.data.localdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TrackDao {
    @Upsert
    suspend fun upsertTrack(track: TrackEntity)

    @Query("SELECT * FROM track_table")
    fun getTrack(): LiveData<List<TrackEntity>?>
}