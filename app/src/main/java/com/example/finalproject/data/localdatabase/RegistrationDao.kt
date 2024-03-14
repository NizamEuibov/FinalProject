package com.example.finalproject.data.localdatabase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.finalproject.ui.loginfragments.model.LoginModel

@Dao
interface RegistrationDao {

    @Insert
    suspend fun InsertUser(user:RegistrationEntity)

    @Query("SELECT * FROM registration_table")
    fun getEmailAndPassword(): LiveData<List<LoginModel>>

    }