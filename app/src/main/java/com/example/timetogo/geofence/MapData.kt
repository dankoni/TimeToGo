package com.example.timetogo.geofence

import android.graphics.Color
import android.location.Location
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapData(val location:Location, val map: GoogleMap) {

    fun addGeofenceMarker() {
        val latLng = LatLng(location.latitude,location.longitude)
        map.addMarker(MarkerOptions().position(latLng).title("Work"))

        val fenceCircle = CircleOptions().center(latLng).radius(150.0).fillColor(0x40ff0000).strokeColor(Color.TRANSPARENT).strokeWidth(2f)
        map.addCircle(fenceCircle)
    }
}