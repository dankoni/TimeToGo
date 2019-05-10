package com.example.timetogo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

@Database(entities = [AppConfig::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appConfigDao(): AppConfigDao

    companion object {
        fun createDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "App_database").addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Executors.newSingleThreadExecutor().execute(prepopulate(db as AppDatabase))
                    }
                })
                .allowMainThreadQueries() //temp move to rx or coorutines
                .build()
        }

        private fun prepopulate(db : AppDatabase): Runnable? {
            return Runnable {
                val appConfig = AppConfig(1,false)
                db.appConfigDao().insert(appConfig)
            }
        }


    }
}