package com.example.timetogo.data

import android.location.Location
import androidx.room.TypeConverter
import com.google.gson.Gson

class LocationConverter {

    @TypeConverter
    fun fromLocation(value: String?): Location? {
        return value?.let { Gson().fromJson(value, Location::class.java) }
    }

    @TypeConverter
    fun toLocation(value: Location?): String? {
        return value?.let { Gson().toJson(value) }
    }

}