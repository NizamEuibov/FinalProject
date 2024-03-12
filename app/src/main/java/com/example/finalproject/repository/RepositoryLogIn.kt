package com.example.finalproject.repository

import androidx.lifecycle.LiveData
import com.example.finalproject.data.localdatabase.RegistrationDao
import com.example.finalproject.data.localdatabase.RegistrationEntity
import com.example.finalproject.ui.login.model.LoginModel
import javax.inject.Inject

class RepositoryLogIn @Inject constructor(private val registrationDao: RegistrationDao) {
    val loginModel:LiveData<List<LoginModel>> = registrationDao.getData()
}