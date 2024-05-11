package com.example.finalproject.ui.homefragment.viewmodel

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
class HomeViewModel @Inject constructor(private val artists: NetworkRepository) : ViewModel() {
    private val _list = MutableLiveData<UIState>(UIState.None)
    val list: LiveData<UIState> = _list

   fun getInformation() {
        viewModelScope.launch {
            _list.value = UIState.Loading(true)
            val response = artists.getArtists()
           _list.value=response
            _list.value=UIState.Loading(false)
        }
    }
}