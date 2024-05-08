package com.example.finalproject.ui.serachfragments.viewmodel

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
class SearchAllViewModel @Inject constructor(repoNetwork: NetworkRepository) : ViewModel() {
    private val _artistsList = MutableLiveData<UIState>()
    val artistsList: LiveData<UIState> = _artistsList

    init {
        viewModelScope.launch {
            _artistsList.value = UIState.Loading(true)
            val response = repoNetwork.getArtists()
            _artistsList.value = response
            _artistsList.value = UIState.Loading(false)
        }
    }
}