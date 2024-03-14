package com.example.finalproject.ui.loginfragments.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.repository.RepositoryLogIn
import com.example.finalproject.ui.loginfragments.model.LoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(private val repositoryLogIn: RepositoryLogIn) :
    ViewModel() {
    private val loginList = MutableLiveData<List<LoginModel>>()


    init {
        repositoryLogIn.loginModel.observeForever {
            loginList.value = it
        }
    }

    fun checkData(data: LoginModel): Boolean? {

        return loginList.value?.contains(data)

    }
}


//email ve parolu yoxlaya bilmirem