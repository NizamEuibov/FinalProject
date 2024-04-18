package com.example.finalproject.ui.libraryfragment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.data.localdatabase.TrackEntity
import com.example.finalproject.repository.repositorylocaldata.RepoSetTrack
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LikedTracksViewModel @Inject constructor( repoSetTrack: RepoSetTrack):ViewModel(){
    private var _likedTracks= MutableLiveData<List<TrackEntity>?>()
    val likedTracks:LiveData<List<TrackEntity>?> =_likedTracks

    init {
        repoSetTrack.trackList.observeForever{
            _likedTracks.value=it
        }
        Log.d("liked","$_likedTracks" )
    }
}