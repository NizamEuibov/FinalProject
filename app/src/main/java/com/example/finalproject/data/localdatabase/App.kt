package com.example.finalproject.data.localdatabase

import android.app.Application
import androidx.room.Room

class App:Application() {
private lateinit var registrationDatabase: RegistrationDatabase

    override fun onCreate() {
        super.onCreate()

registrationDatabase= Room.databaseBuilder(
    applicationContext,RegistrationDatabase::class.java,
    "app-database"
).allowMainThreadQueries()
    .build()

        AppDataBase.registrationDatabase=registrationDatabase
    }
}