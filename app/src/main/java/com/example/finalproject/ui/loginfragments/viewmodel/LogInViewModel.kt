package com.example.finalproject.ui.loginfragments.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.repository.repositorylocaldata.RegistrationRepository
import com.example.finalproject.ui.loginfragments.model.LoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor( private val registrationRepository: RegistrationRepository) : ViewModel() {
    private val _loginList = MutableLiveData<List<LoginModel>?>()
    fun userId(email:String):LiveData<Int?>{
        return registrationRepository.getUserId(email)
    }
    init {
       registrationRepository.loginModel.observeForever {
            _loginList.value = it
           Log.d("UserId","$it")
        }
    }
    fun checkData(data:LoginModel): Boolean? {
        return _loginList.value?.contains(data)

    }
}
