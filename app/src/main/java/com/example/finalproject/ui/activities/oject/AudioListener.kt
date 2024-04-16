package com.example.finalproject.ui.activities.oject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.data.networkdata.models.DataTypeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(): ViewModel() {

     private val _data = MutableLiveData<DataTypeModel.Tracks>()
     val data: LiveData<DataTypeModel.Tracks> = _data

     fun setData(data: DataTypeModel.Tracks) {
          _data.value = data
          Log.d("Tracks1","$data")
     }
}

data class AudioData(
     val audio:String?=null,
     val id:Int?=null
)