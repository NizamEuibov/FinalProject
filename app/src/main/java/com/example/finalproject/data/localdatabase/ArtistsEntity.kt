package com.example.finalproject.data.localdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists_table")
data class ArtistsEntity(
    @PrimaryKey(autoGenerate = true)
    val id :Int,
    val userId:Int,
   val artistsId :List<Int?>
)



