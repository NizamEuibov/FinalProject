package com.example.finalproject.repository.repositorylocaldata

import com.example.finalproject.data.localdatabase.RegistrationDao
import com.example.finalproject.data.localdatabase.TrackEntity
import javax.inject.Inject

class RepoSetTrack @Inject constructor(private val trackDao: RegistrationDao) {
    suspend fun setTrackToDatabase(track:TrackEntity){

    }
}