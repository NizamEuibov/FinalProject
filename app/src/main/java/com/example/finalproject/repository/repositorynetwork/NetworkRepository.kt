package com.example.finalproject.repository.repositorynetwork

import com.example.finalproject.data.networkdata.apiservices.ArtistApiService
import com.example.finalproject.data.networkdata.models.DataTypeModel
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val artistApiService: ArtistApiService) {

    suspend fun getArtists(): List<DataTypeModel.NameAndImage>? {
        val response = artistApiService.getArtistsApi()
       return response.body()?.result
    }
}