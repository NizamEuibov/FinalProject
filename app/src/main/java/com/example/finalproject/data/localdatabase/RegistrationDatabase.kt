package com.example.finalproject.data.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//@Database(entities = [RegistrationEntity::class], version = 1)
//abstract class RegistrationDatabase : RoomDatabase() {
//    abstract fun registrationDao(): RegistrationDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: RegistrationDatabase? = null
//
//        fun getDatabase(context: Context): RegistrationDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    RegistrationDatabase::class.java,
//                    "signUpDatabase"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}
