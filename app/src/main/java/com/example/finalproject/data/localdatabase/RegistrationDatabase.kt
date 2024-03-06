package com.example.finalproject.data.localdatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RegistrationEntity::class], version = 1)
abstract class RegistrationDatabase : RoomDatabase() {

    abstract fun registrationDao(): RegistrationDao

}