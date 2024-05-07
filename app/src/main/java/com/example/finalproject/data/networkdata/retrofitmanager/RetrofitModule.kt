package com.example.finalproject.data.networkdata.retrofitmanager

import com.example.finalproject.data.networkdata.apiservices.ArtistApiService
import com.example.finalproject.ui.`object`.ConstVal.BASEURL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun retrofitArtists():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
@Provides
    fun retrofitInstance(apiService:Retrofit):ArtistApiService{
        return apiService.create(ArtistApiService::class.java)

    }

}

