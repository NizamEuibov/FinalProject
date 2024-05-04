package com.example.finalproject.ui.artistsfragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.repository.repositorylocaldata.RegistrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserIdViewModel @Inject constructor(private val registrationRepository: RegistrationRepository) :
    ViewModel() {
    fun getUserId(email: String): LiveData<Int?> {
        return registrationRepository.getUserId(email)
    }

}