package com.example.finalproject.data.networkdata.apiservices


import com.example.finalproject.data.networkdata.models.DataTypeModel
import com.example.finalproject.ui.`object`.ConstVal.ARTISTSALBUMSAPI
import com.example.finalproject.ui.`object`.ConstVal.TRACKAPI
import retrofit2.Response
import retrofit2.http.GET

interface ArtistApiService {

    @GET(ARTISTSALBUMSAPI)
    suspend fun getArtistsApi():Response<DataTypeModel.Model>


    @GET(TRACKAPI)
    suspend fun getTracks():Response<DataTypeModel.Model>
}