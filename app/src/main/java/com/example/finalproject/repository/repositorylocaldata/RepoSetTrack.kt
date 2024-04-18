package com.example.finalproject.repository.repositorylocaldata

import androidx.lifecycle.LiveData
import com.example.finalproject.data.localdatabase.TrackDao
import com.example.finalproject.data.localdatabase.TrackEntity
import javax.inject.Inject

class RepoSetTrack @Inject constructor(private val trackDao: TrackDao) {
  val trackList: LiveData<List<TrackEntity>?> =trackDao.getTrack()
    suspend fun setTrackToDatabase(track:TrackEntity){
      trackDao.upsertTrack(track)
    }
}