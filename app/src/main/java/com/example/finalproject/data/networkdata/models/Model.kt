package com.example.finalproject.data.networkdata.models

import com.google.gson.annotations.SerializedName
 sealed class DataTypeModel {
     data class Model(
         @SerializedName("results")
         val result: List<NameAndImage>
     ):DataTypeModel()

     data class NameAndImage(
         @SerializedName("id")
         val id: Int? = null,
         @SerializedName("name")
         val name: String,
         @SerializedName("image")
         val image: String,
         @SerializedName("albums")
         val albums: List<AlbumList>,
         @SerializedName("tracks")
         val tracks: List<Tracks>
     ):DataTypeModel()

     data class AlbumList(
         @SerializedName("id")
         val id: Int? = null,
         @SerializedName("name")
         val name: String? = null,
         @SerializedName("image")
         val image: String? = null
     ):DataTypeModel()

     data class Tracks(
         @SerializedName("album_id")
         val albumId:Int?=null,
         @SerializedName("id")
         val id:Int?=null,
         @SerializedName("name")
         val name:String?=null,
         @SerializedName("image")
         val image:String?=null,
         @SerializedName("audio")
         val audio:String?=null,
         @SerializedName("album_name")
         val albumName:String?=null,
     ):DataTypeModel()
 }



sealed class UIState{
    data object None : UIState()
    data class Loading(val isLoading: Boolean) : UIState()
    data class Data(val data: List<DataTypeModel.NameAndImage>?) : UIState()
    data class Error(val error: String) : UIState()
}