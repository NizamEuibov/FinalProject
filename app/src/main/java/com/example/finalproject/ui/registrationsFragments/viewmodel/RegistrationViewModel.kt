package com.example.finalproject.ui.registrationsFragments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.localdatabase.RegistrationEntity
import com.example.finalproject.repository.repositorylocaldata.RegistrationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val registrationRepository: RegistrationRepository) :
    ViewModel() {
    lateinit var toast: String

    fun sendDataToRepository(user: RegistrationEntity, callback: (Boolean) -> Unit) {
        registrationRepository.allData.observeForever {
            if (it != null) {
                val emailList = it.map{it.email}
                if (emailList.contains(user.email)) {
                    callback(false)
                }
                else{
                    viewModelScope.launch {
                        registrationRepository.sendDataToDatabase(user)
                        callback(true)
                    }

                }
            }
        }
    }
}


