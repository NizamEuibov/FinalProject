package com.example.finalproject.ui.track.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.networkdata.models.UIState
import com.example.finalproject.repository.repositorynetwork.TracksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackViewModel @Inject constructor(private val repoTracks: TracksRepository) : ViewModel() {
    private val _trackList = MutableLiveData<UIState>()
    val trackList: LiveData<UIState> = _trackList

    fun fetchTracks() {
        viewModelScope.launch {
            _trackList.value=UIState.Loading(true)
            val response = repoTracks.getTracks()
            _trackList.value = response
            _trackList.value=UIState.Loading(false)
        }
    }
}