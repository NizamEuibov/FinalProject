package com.example.finalproject

import okhttp3.MediaType.Companion.toMediaTypeOrNull






import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

fun main() {
    val client = OkHttpClient()

    val mediaType = "application/x-www-form-urlencoded".toMediaTypeOrNull()
    val body = RequestBody.create(mediaType, "id=%3CREQUIRED%3E&access_token=%3CREQUIRED%3E")
    val request = Request.Builder()
        .url("https://spotifyuserapiserg-osipchukv1.p.rapidapi.com/getCategory")
        .post(body)
        .addHeader("content-type", "application/x-www-form-urlencoded")
        .addHeader("X-RapidAPI-Key", "fe4580eaadmsh348616aa018be0dp13dbcejsn37013975a36b")
        .addHeader("X-RapidAPI-Host", "SpotifyUserAPIserg-osipchukV1.p.rapidapi.com")
        .build()

    try {
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            println("Response: $responseBody")
        } else {
            println("Request failed with code: ${response.code}")
        }
    } catch (e: IOException) {
        println("Error occurred: ${e.message}")
    }
}
