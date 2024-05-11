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
class LogInViewModel @Inject constructor(private val registrationRepository: RegistrationRepository) :
    ViewModel() {
    private val _loginList = MutableLiveData<List<LoginModel>?>()
    val _list =MutableLiveData<UiStateLogin>(UiStateLogin.None)
    val list :LiveData<UiStateLogin> =_list
    fun userId(email: String): LiveData<Int?> {
        return registrationRepository.getUserId(email)
    }

    init {
        registrationRepository.loginModel.observeForever {
            _loginList.value = it
            Log.d("UserId", "$it")
        }
    }

    fun checkData(data: LoginModel): Boolean? {
        return _loginList.value?.contains(data)

    }

    fun uiState():{
//It is not completed
    }
}

sealed class UiStateLogin {
    data object None : UiStateLogin()
    data class Loading(val isLoading: Boolean) : UiStateLogin()
    data class Data(val data: Boolean) : UiStateLogin()
    data class Error(val error: String) : UiStateLogin()
}
