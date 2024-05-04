package com.example.finalproject.ui.artistsfragment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.localdatabase.ArtistsEntity
import com.example.finalproject.repository.repositorylocaldata.ArtistsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistsIdViewModel @Inject constructor(private val artistsRepository: ArtistsRepository) :
    ViewModel() {

    fun sendArtistsIdToRepository(artist: ArtistsEntity) {
        viewModelScope.launch {
            Log.d("Artists","$artist")
            artistsRepository.addArtistsIdToDatabase(artist)
        }
    }
}