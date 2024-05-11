package com.example.finalproject.ui.libraryfragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.repository.repositorylocaldata.RegistrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserNameViewModel @Inject constructor(private val registrationRepository: RegistrationRepository) :
    ViewModel() {
    fun getUserName(id: Int): LiveData<String> {
       return registrationRepository.getUserName(id)
    }

    fun updateUserName(id: Int,userName:String){
        viewModelScope.launch {
            registrationRepository.updateUserName(id, userName)
        }
    }
}