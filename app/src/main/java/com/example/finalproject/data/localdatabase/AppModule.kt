package com.example.finalproject.data.localdatabase

import android.app.Application
import androidx.room.Room
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
    )
        .build()
}

@Provides
fun provideRegistrationDao(registrationDatabase: RegistrationDatabase): RegistrationDao {
    return registrationDatabase.registrationDao()
}






}





