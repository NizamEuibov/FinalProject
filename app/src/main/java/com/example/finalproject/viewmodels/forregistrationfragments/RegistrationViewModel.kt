package com.example.finalproject.viewmodels.forregistrationfragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.localdatabase.RegistrationEntity
import com.example.finalproject.repository.SendDataToRoom
import kotlinx.coroutines.delay

import kotlinx.coroutines.launch

class RegistrationViewModel(private val repository: SendDataToRoom) : ViewModel() {

    var email: String = ""
    var password: String = ""
    var gender: String = ""
    var name: String = ""
   init {
      sendDataToDatabase()
   }

    private fun sendDataToDatabase() {
        val user = RegistrationEntity(0, email, password, gender, name)
        viewModelScope.launch {
            delay(5000)
            repository.sendDataToDatabase(user)
        }
    }
}