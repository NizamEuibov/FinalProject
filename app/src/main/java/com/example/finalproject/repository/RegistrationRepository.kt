package com.example.finalproject.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.finalproject.data.localdatabase.RegistrationDao
import com.example.finalproject.data.localdatabase.RegistrationEntity
import com.example.finalproject.ui.login.model.LoginModel
import javax.inject.Inject

class RegistrationRepository @Inject constructor(private val registrationDao: RegistrationDao) {
    val loginModel:LiveData<List<LoginModel>> = registrationDao.getData()
    suspend fun sendDataToDatabase(user: RegistrationEntity) {
        Log.d("user12",loginModel.toString())
        registrationDao.upsertUser(user)
    }
}