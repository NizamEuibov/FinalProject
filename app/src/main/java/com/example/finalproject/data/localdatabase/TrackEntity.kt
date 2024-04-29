package com.example.finalproject.data.localdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("track")
data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
    val number:Int,
    val trackName: String,
    val trackImage: String,
    val trackAudio: String
)
