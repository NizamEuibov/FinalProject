package com.example.finalproject.repository.repositorylocaldata

import androidx.lifecycle.LiveData
import com.example.finalproject.data.localdatabase.RegistrationDao
import com.example.finalproject.data.localdatabase.RegistrationEntity
import com.example.finalproject.ui.loginfragments.model.LoginModel
import javax.inject.Inject

class RegistrationRepository @Inject constructor(private val registrationDao: RegistrationDao) {
    val loginModel: LiveData<List<LoginModel>?> = registrationDao.getEmailAndPassword()
    val allData: LiveData<List<RegistrationEntity>?> = registrationDao.getAllData()
    fun getUserName(id:Int):LiveData<String> = registrationDao.getUserName(id)
    fun getUserId(email:String):LiveData<Int?> =registrationDao.getUserId(email)
    suspend fun sendDataToDatabase(user: RegistrationEntity) {
        registrationDao.insertUser(user)
    }

    suspend fun updateUserName(id: Int,userName:String){
        registrationDao.updateUserName(id, userName)
    }


}