package com.example.finalproject.data.localdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.finalproject.ui.loginfragments.model.LoginModel

@Dao
interface RegistrationDao {

    @Insert
    suspend fun insertUser(user: RegistrationEntity)

    @Query("SELECT id FROM registration_entity WHERE email =:email")
    fun getUserId(email: String): LiveData<Int?>

    @Query("SELECT id, email, password FROM registration_entity ")
    fun getEmailAndPassword(): LiveData<List<LoginModel>?>


    @Query("SELECT * FROM registration_entity")
    fun getAllData(): LiveData<List<RegistrationEntity>?>

    @Query("SELECT userName FROM registration_entity WHERE id = :id")
    fun getUserName(id: Int): LiveData<String>

}