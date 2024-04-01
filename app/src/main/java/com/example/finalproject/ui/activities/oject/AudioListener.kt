package com.example.finalproject.ui.activities.oject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(): ViewModel() {
    private val _data = MutableLiveData<String>()
    val data: LiveData<String> get() = _data

    fun setData(newData: String) {
        _data.value = newData
    }
}