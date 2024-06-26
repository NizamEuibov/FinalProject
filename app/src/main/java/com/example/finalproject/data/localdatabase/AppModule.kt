package com.example.finalproject.data.localdatabase

import android.app.Application
import androidx.room.Room
import com.example.finalproject.ui.`object`.ConstVal.APP
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {


    @Provides
    fun provideRoom(applicationContext: Application): RegistrationDatabase {
        return Room.databaseBuilder(
            applicationContext,
            RegistrationDatabase::class.java,
            APP  //add like const
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideRegistrationDao(registrationDatabase: RegistrationDatabase): RegistrationDao {
        return registrationDatabase.registrationDao()
    }

    @Provides
    fun provideTrackDao(registrationDatabase: RegistrationDatabase): TrackDao {
        return registrationDatabase.trackDao()
    }

    @Provides
    fun provideArtistsDao(registrationDatabase: RegistrationDatabase): ArtistsDao {
        return registrationDatabase.artistDao()
    }


}





