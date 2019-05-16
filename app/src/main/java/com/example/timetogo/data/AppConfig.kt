package com.example.timetogo.data

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_config")
data class AppConfig(@PrimaryKey val id: Int, val location: Location, val timestamp: Long)