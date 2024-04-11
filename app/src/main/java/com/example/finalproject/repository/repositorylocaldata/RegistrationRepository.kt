package com.example.finalproject.repository.repositorylocaldata

import com.example.finalproject.data.localdatabase.RegistrationDao
import com.example.finalproject.data.localdatabase.RegistrationEntity
import javax.inject.Inject

class RegistrationRepository @Inject constructor(private val registrationDao: RegistrationDao) {
    suspend fun sendDataToDatabase(user: RegistrationEntity) {
        registrationDao.insertUser(user)
    }


}