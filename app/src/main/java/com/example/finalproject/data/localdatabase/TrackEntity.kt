package com.example.finalproject.data.localdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("track_table")
data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val trackName:String,
    val trackAudio:String
)
