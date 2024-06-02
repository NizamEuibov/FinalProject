package com.example.finalproject.data.localdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(
    entities = [RegistrationEntity::class, TrackEntity::class, ArtistsEntity::class],
    version = 29, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RegistrationDatabase : RoomDatabase() {
    abstract fun registrationDao(): RegistrationDao
    abstract fun trackDao(): TrackDao
  abstract fun artistDao():ArtistsDao
}

class Converters {
    @TypeConverter
    fun fromList(list: List<Int?>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(data: String): List<Int?> {
        return data.split(",").map { if (it.isBlank()) null else it.toInt() }
    }
}