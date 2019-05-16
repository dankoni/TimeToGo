package com.example.timetogo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AppConfig::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appConfigDao(): AppConfigDao

    companion object {
        fun createDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "App_database")
                .allowMainThreadQueries() //temp move to rx or coorutines
                .build()
        }
    }
}