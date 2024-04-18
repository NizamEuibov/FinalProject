package com.example.finalproject.data.localdatabase

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(24, 25) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS track_table (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "number INTEGER NOT NULL, " +
                "trackName TEXT NOT NULL, " +
                "trackImage TEXT NOT NULL, " +
                "trackAudio TEXT NOT NULL)")
    }
}