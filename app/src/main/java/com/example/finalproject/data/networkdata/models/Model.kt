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
         val albums: List<AlbumList>
     ):DataTypeModel()

     data class AlbumList(
         @SerializedName("id")
         val id: String? = null,
         @SerializedName("name")
         val name: String? = null,
         @SerializedName("image")
         val image: String? = null
     ):DataTypeModel()
 }