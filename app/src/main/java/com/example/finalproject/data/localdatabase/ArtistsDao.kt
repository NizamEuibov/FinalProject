package com.example.finalproject.data.localdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ArtistsDao {
  @Insert
  suspend fun  addArtistsToDatabase(artists:ArtistsEntity)

  @Query("SELECT * FROM artists_table WHERE userId = :id")
  fun getArtistsList(id: Int): LiveData<ArtistsEntity?>
}