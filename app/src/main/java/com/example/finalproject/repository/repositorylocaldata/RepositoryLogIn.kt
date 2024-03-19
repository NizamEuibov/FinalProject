package com.example.finalproject.repository.repositorylocaldata

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.finalproject.data.localdatabase.RegistrationDao
import com.example.finalproject.data.localdatabase.RegistrationEntity
import com.example.finalproject.ui.loginfragments.model.LoginModel
import javax.inject.Inject

class RepositoryLogIn @Inject constructor(registrationDao: RegistrationDao) {
    val loginModel:LiveData<List<LoginModel>> = registrationDao.getEmailAndPassword()

}