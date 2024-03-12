package com.example.finalproject.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.repository.RepositoryLogIn
import com.example.finalproject.ui.login.model.LoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(repositoryLogIn: RepositoryLogIn) : ViewModel() {
    private val loginModel: LiveData<List<LoginModel>> = repositoryLogIn.loginModel

    fun checkData(data: LoginModel): Boolean? {
        return loginModel.value?.contains(data)


    }
}

//email ve parolu yoxlaya bilmirem