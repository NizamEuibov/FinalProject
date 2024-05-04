package com.example.finalproject.ui.loginfragments.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.repository.repositorylocaldata.RegistrationRepository
import com.example.finalproject.ui.loginfragments.model.LoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor( registrationRepository: RegistrationRepository) : ViewModel() {
    private val loginList = MutableLiveData<List<LoginModel>?>()
    init {
       registrationRepository.loginModel.observeForever {
            loginList.value = it
        }
    }
    fun checkData(data: LoginModel): Boolean? {
        return loginList.value?.contains(data)

    }
}
