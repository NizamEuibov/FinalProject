package com.example.finalproject.data.networkdata.models

import com.google.gson.annotations.SerializedName

data class Model(
    @SerializedName("results")
    val result: List<NameAndImage>
)

data class NameAndImage(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("albums")
    val albums: List<AlbumList>
)

data class AlbumList(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("image")
    val image: String? = null
)
