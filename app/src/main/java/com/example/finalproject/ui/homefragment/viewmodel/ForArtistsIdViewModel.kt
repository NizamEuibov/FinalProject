package com.example.finalproject.ui.homefragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.data.localdatabase.ArtistsEntity
import com.example.finalproject.repository.repositorylocaldata.ArtistsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForArtistsIdViewModel @Inject constructor(private val artistsRepository: ArtistsRepository) :
    ViewModel() {
     fun artistsId(userId: Int): LiveData<ArtistsEntity?> {
            return artistsRepository.artistsId(userId)
        }

}