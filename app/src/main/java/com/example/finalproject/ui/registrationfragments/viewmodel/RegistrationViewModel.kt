package com.example.finalproject.ui.registrationfragments.viewmodel

import androidx.lifecycle.ViewModel
import com.example.finalproject.data.localdatabase.RegistrationEntity


class RegistrationViewModel : ViewModel() {

    fun sendDataToDatabase(user: RegistrationEntity) {
        println("user:$user")

    }
}


