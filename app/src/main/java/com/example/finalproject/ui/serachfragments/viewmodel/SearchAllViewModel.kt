package com.example.finalproject.ui.serachfragments.viewmodel

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
class SearchAllViewModel @Inject constructor(repoNetwork: RepoNetwork) : ViewModel() {
    private val _artistsList = MutableLiveData<List<DataTypeModel.NameAndImage>>()
    val artistsList: LiveData<List<DataTypeModel.NameAndImage>> = _artistsList

    init {
        viewModelScope.launch {
            _artistsList.value = repoNetwork.getArtists()
        }
    }
}