package com.example.finalproject.ui.artistsfragment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.repository.repositorynetwork.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistsViewModel @Inject constructor(private val repoNetwork: NetworkRepository) :ViewModel(){
       private val _artistsLiveData=MutableLiveData<List<DataTypeModel.NameAndImage>?>()
     val artistsLiveData:LiveData<List<DataTypeModel.NameAndImage>?> =_artistsLiveData


    init {
        getArtists()
    }
    private fun getArtists() {
        viewModelScope.launch {

            _artistsLiveData.value=repoNetwork.getArtists()
            Log.d("user54","$_artistsLiveData")

        }

    }
}