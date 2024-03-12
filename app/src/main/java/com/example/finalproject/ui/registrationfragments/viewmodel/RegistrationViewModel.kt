package com.example.finalproject.ui.registrationfragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.localdatabase.RegistrationEntity
import com.example.finalproject.repository.RegistrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val repo:RegistrationRepository) : ViewModel() {

    fun sendDataToRepository(user: RegistrationEntity) {
        viewModelScope.launch {
            repo.sendDataToDatabase(user)
        }

    }
}


