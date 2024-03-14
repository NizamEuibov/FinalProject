package com.example.finalproject.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.finalproject.data.localdatabase.RegistrationDao
import com.example.finalproject.data.localdatabase.RegistrationEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationRepository @Inject constructor(private val registrationDao: RegistrationDao) {
    suspend fun sendDataToDatabase(user: RegistrationEntity) {
        registrationDao.InsertUser(user)
    }


}