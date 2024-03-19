package com.example.finalproject.repository.repositorynetwork

import com.example.finalproject.data.networkdata.apiservices.ArtistApiService
import com.example.finalproject.data.networkdata.models.NameAndImage
import javax.inject.Inject

class RepoNetwork @Inject constructor(private val artistApiService: ArtistApiService) {

    suspend fun getArtists(): List<NameAndImage>? {
        val response = artistApiService.getArtistsApi()
       return response.body()?.result


    }
}