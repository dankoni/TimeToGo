package com.example.timetogo

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timetogo.geofence.GeofenceData
import com.example.timetogo.geofence.MapData
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap

class TimeViewModel (val appConfig: SharedPreferences): ViewModel() {

    val geofenceIsAdded = MutableLiveData<Boolean>()

    lateinit var map: GoogleMap



    fun isGeofenceAdded(): LiveData<Boolean> {
        return geofenceIsAdded
    }

    @SuppressLint("MissingPermission")
    fun setGeofence(location: Location, context: Context) {
        val geoFenceData = GeofenceData(location, context)

        val geofencingClient = LocationServices.getGeofencingClient(context)

        geofencingClient?.addGeofences(geoFenceData.geoFenceRequest, geoFenceData.geoFencePendingIntent)?.run {
            addOnSuccessListener {
                appConfig.edit().putBoolean("isFirstLaunch",false).commit()
                geofenceIsAdded.postValue(true)
            }
            addOnFailureListener {
                geofenceIsAdded.postValue(false)
            }
        }
    }

    fun whenMapIsReady(googleMap: GoogleMap) {
        map = googleMap
    }

    fun drawMarker(location: Location) {
        val mapData = MapData(location, map)
        mapData.addGeofenceMarker()
    }
}