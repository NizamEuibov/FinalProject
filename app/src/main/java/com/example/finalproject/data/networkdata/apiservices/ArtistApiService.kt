package com.example.finalproject.data.networkdata.apiservices


import com.example.finalproject.data.networkdata.models.DataTypeModel
import retrofit2.Response
import retrofit2.http.GET

interface ArtistApiService {

    @GET("/v3.0/artists/albums/?client_id=1a88e795")
    suspend fun getArtistsApi():Response<DataTypeModel.Model>


    @GET("/v3.0/artists/tracks/?client_id=1a88e795")
    suspend fun getTracks():Response<DataTypeModel.Model>
}