package com.example.finalproject.data.localdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TrackDao {
    @Upsert
    suspend fun upsertTrack(track: TrackEntity)

    @Delete
    suspend fun deleteTrack(track: TrackEntity)

    @Query("SELECT * FROM track")
    fun getTrack(): LiveData<List<TrackEntity>?>
}