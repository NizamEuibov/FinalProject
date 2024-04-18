package com.example.finalproject.data.localdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TrackDao {
    @Insert
    suspend fun upsertTrack(track: TrackEntity)

    @Query("SELECT * FROM track_table")
    fun getTrack(): LiveData<List<TrackEntity>?>
}