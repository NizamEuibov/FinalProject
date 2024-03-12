package com.example.finalproject.data.localdatabase

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRoom(applicationContext: Application): RegistrationDatabase {
        return Room.databaseBuilder(
            applicationContext,
            RegistrationDatabase::class.java,
            "app-database"
        ).build()
    }

    @Provides
    fun provideRegistrationDao(registrationDatabase: RegistrationDatabase): RegistrationDao {
        return registrationDatabase.registrationDao()
    }
}

object MyData {
   lateinit var appDatabase: RegistrationDatabase
}

@Database(entities = [RegistrationEntity::class], version = 1)
abstract class RegistrationDatabase : RoomDatabase() {
    abstract fun registrationDao(): RegistrationDao
}



