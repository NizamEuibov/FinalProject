package com.example.finalproject.data.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RegistrationEntity::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun registrationDao(): RegistrationDao

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getDatabase(context: Context): DataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "signUpDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}