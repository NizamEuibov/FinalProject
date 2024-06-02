package com.example.finalproject.data.localdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "registration_entity")
data class RegistrationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val email: String,
    val password: String,
    val gender: String,
    val userName: String,
    val login:Boolean
)
