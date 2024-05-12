package com.example.finalproject.repository.repositorylocaldata

import androidx.lifecycle.LiveData
import com.example.finalproject.data.localdatabase.TrackDao
import com.example.finalproject.data.localdatabase.TrackEntity
import javax.inject.Inject

class SetTrackRepository @Inject constructor(private val trackDao: TrackDao) {
  val trackList: LiveData<List<TrackEntity>?> =trackDao.getTrack()
    suspend fun setTrackToDatabase(track:TrackEntity){
      trackDao.upsertTrack(track)
    }

  suspend fun deleteTrackFromDatabase(userId:Int){
    trackDao.deleteTrack(userId)
  }
}