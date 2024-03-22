package com.example.finalproject.ui.albumsfragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.repository.repositorynetwork.RepoNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(private val repoNetwork: RepoNetwork) : ViewModel() {
    private val _albumsList = MutableLiveData<List<DataTypeModel.NameAndImage>>()
  val albumsList: LiveData<List<DataTypeModel.NameAndImage>> = _albumsList


    init {
        viewModelScope.launch {
            _albumsList.value = repoNetwork.getArtists()
        }
    }
}