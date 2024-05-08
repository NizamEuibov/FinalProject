package com.example.finalproject.ui.albumsfragment.viewmodel

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
class TrackViewModel @Inject constructor(private val repoTracks: TracksRepository) :ViewModel(){
    private val _tracksList=MutableLiveData<UIState>()
    val trackList:LiveData<UIState> =_tracksList
    init {
        viewModelScope.launch {
            _tracksList.value=UIState.Loading(true)
           _tracksList.value= repoTracks.getTracks()
            _tracksList.value = UIState.Loading(false)
        }
    }

}