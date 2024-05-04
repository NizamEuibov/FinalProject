package com.example.finalproject.ui.track.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.localdatabase.TrackEntity
import com.example.finalproject.repository.repositorylocaldata.SetTrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class
SendTrackToRepo @Inject constructor(private val repoSetTrack: SetTrackRepository):ViewModel() {

    fun sendTrackToRepo(track: TrackEntity){
        viewModelScope.launch {
            repoSetTrack.setTrackToDatabase(track)
            Log.d("Tracks1","$track")
        }
    }


    fun deleteTrackFromDatabase(track: TrackEntity){
        viewModelScope.launch {
            repoSetTrack.deleteTrackFromDatabase(track)
        }
    }
}