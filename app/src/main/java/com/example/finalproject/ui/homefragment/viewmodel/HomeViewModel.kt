package com.example.finalproject.ui.homefragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.networkdata.models.NameAndImage
import com.example.finalproject.repository.repositorynetwork.RepoNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val artists: RepoNetwork) : ViewModel() {
    private val _list = MutableLiveData<List<NameAndImage>?>()
    val list: LiveData<List<NameAndImage>?> = _list

    init {
        getInformation()
    }

    private fun getInformation() {
        viewModelScope.launch {
            _list.value = artists.getArtists()

        }

    }
}