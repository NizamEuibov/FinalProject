package com.example.finalproject.repository.repositorylocaldata

import androidx.lifecycle.LiveData
import com.example.finalproject.data.localdatabase.ArtistsDao
import com.example.finalproject.data.localdatabase.ArtistsEntity
import javax.inject.Inject

class ArtistsRepository @Inject constructor(private val artistsDao: ArtistsDao) {

   fun artistsId(userId:Int):LiveData<ArtistsEntity?> =artistsDao.getArtistsList(userId)

    suspend fun addArtistsIdToDatabase(artistsId:ArtistsEntity){
        artistsDao.addArtistsToDatabase(artistsId)
    }
}