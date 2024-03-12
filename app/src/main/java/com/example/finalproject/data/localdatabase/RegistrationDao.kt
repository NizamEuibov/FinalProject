package com.example.finalproject.data.localdatabase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.finalproject.ui.login.model.LoginModel

@Dao
interface RegistrationDao {

    @Upsert
    suspend fun upsertUser(user:RegistrationEntity){
        Log.d("user121", user.toString())
    }

    @Query("SELECT email,password FROM registration_table")
  fun getData():LiveData<List<LoginModel>>

    }