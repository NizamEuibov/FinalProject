package com.example.finalproject.ui.libraryfragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.repository.repositorylocaldata.RegistrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserNameViewModel @Inject constructor(private val registrationRepository: RegistrationRepository):ViewModel() {
    fun getUserName(id:Int):LiveData<String> = registrationRepository.getUserName(id)
}