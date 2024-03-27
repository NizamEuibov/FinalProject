package com.example.finalproject.repository.repositorynetwork

import com.example.finalproject.data.networkdata.apiservices.ArtistApiService
import com.example.finalproject.data.networkdata.models.DataTypeModel
import javax.inject.Inject

class RepoTracks @Inject constructor(private val artistApiService: ArtistApiService){

    suspend fun getTracks():List<DataTypeModel.NameAndImage>?{
        val response=artistApiService.getTracks()
        return response.body()?.result
    }
}