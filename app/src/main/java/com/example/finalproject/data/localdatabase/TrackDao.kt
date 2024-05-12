package com.example.finalproject.data.localdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TrackDao {
    @Upsert
    suspend fun upsertTrack(track: TrackEntity)

    @Query("DELETE FROM tracks_like WHERE userId = :userId")
    suspend fun deleteTrack(userId:Int)

    @Query("SELECT * FROM tracks_like")
    fun getTrack(): LiveData<List<TrackEntity>?>
}