package com.example.finalproject.ui.libraryfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.data.localdatabase.TrackDao
import com.example.finalproject.data.localdatabase.TrackEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LikedTracksViewModel @Inject constructor(private val trackDao: TrackDao):ViewModel(){
    private val _likedTracks =MutableLiveData<List<TrackEntity>?>()
    private val likedTracks:LiveData<List<TrackEntity>?> =_likedTracks


    init {
        _likedTracks.value=trackDao.getTrack()
    }
}