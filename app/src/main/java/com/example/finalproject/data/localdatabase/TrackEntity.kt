package com.example.finalproject.data.localdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tracks_like")
data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
    val number: Int,
    val userId: Int?,
    val trackName: String,
    val trackImage: String,
    val trackAudio: String
)
