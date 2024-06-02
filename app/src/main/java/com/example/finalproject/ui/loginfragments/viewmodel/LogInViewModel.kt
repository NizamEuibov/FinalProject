package com.example.finalproject.ui.loginfragments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.repository.repositorylocaldata.RegistrationRepository
import com.example.finalproject.ui.loginfragments.model.LoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(private val registrationRepository: RegistrationRepository) :
    ViewModel() {
     val loginList = MutableLiveData<UiStateLogin>(UiStateLogin.None)
    fun userId(email: String): LiveData<Int?> {
        return registrationRepository.getUserId(email)
    }

    fun checkData(data: LoginModel) {
        loginList.value = UiStateLogin.Loading(true)
        registrationRepository.loginModel.observeForever { list ->
            val check = list?.contains(data)
            if (check == true) {
                loginList.value = UiStateLogin.Data(check)
            } else {
                loginList.value = UiStateLogin.Error("Data no found")
            }
            loginList.value = UiStateLogin.Loading(false)
        }
    }
}

sealed class UiStateLogin {
    data object None : UiStateLogin()
    data class Loading(val isLoading: Boolean) : UiStateLogin()
    data class Data(val data: Boolean) : UiStateLogin()
    data class Error(val error: String) : UiStateLogin()
}
