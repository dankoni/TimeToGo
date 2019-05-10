package com.example.timetogo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppConfigDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(appConfig: AppConfig)


    @Query("Select * from app_config")
    fun getAppConfig ():AppConfig
}