package com.example.finalproject.ui.artistsfragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.networkdata.models.UIState
import com.example.finalproject.repository.repositorynetwork.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistsViewModel @Inject constructor(private val repoNetwork: NetworkRepository) :
    ViewModel() {
    private var _artistsLiveData = MutableLiveData<UIState>()
    val artistsLiveData: LiveData<UIState> = _artistsLiveData


    init {
        viewModelScope.launch {
            _artistsLiveData.value = UIState.Loading(true)
            val response = repoNetwork.getArtists()
            _artistsLiveData.value =response
            _artistsLiveData.value=UIState.Loading(false)
        }
    }
}