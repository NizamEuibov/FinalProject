package com.example.finalproject.repository.repositorynetwork

import com.example.finalproject.data.networkdata.apiservices.ArtistApiService
import com.example.finalproject.data.networkdata.models.UIState
import com.example.finalproject.ui.`object`.ConstVal.ERROR
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val artistApiService: ArtistApiService) {

    suspend fun getArtists(): UIState {
        return try {
            val response = artistApiService.getArtistsApi()
            if (response.isSuccessful) {
                UIState.Data(response.body()?.result ?: emptyList())
            } else {
                UIState.Error(ERROR)
            }
        } catch (e: Exception) {
            UIState.Error(ERROR)
        }
    }
}