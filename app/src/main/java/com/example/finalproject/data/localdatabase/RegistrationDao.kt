package com.example.finalproject.data.localdatabase

import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface RegistrationDao {
    @Upsert
    suspend fun upsertUser(user:RegistrationEntity)
}