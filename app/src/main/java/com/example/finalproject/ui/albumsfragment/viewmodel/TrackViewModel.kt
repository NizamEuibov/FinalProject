package com.example.finalproject.ui.albumsfragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.repository.repositorynetwork.TracksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class TrackViewModel @Inject constructor(private val repoTracks: TracksRepository) :ViewModel(){
    private val _tracksList=MutableLiveData<List<DataTypeModel.NameAndImage>?>()
    val trackList:LiveData<List<DataTypeModel.NameAndImage>?> =_tracksList
    var audioPair: MutableLiveData<Pair<String, Int>> = MutableLiveData()
    init {
        viewModelScope.launch {
           _tracksList.value= repoTracks.getTracks()
        }
    }

}