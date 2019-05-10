package com.example.timetogo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_config")
data class AppConfig (@PrimaryKey val id:Int, val isFirstLaunch : Boolean)