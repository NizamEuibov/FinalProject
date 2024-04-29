package com.example.finalproject.data.localdatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RegistrationEntity::class, TrackEntity::class],
    version = 25, exportSchema = false)
abstract class RegistrationDatabase : RoomDatabase() {
    abstract fun registrationDao(): RegistrationDao
    abstract fun trackDao(): TrackDao

}