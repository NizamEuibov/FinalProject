package com.example.finalproject.data.localdatabase

object AppDataBase {
    var registrationDatabase: RegistrationDatabase? = null
    fun setDatabase(registrationDatabase: RegistrationDatabase) {
        this.registrationDatabase = registrationDatabase
    }
}