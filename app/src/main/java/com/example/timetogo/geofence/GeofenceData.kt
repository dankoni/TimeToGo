package com.example.timetogo.geofence

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest

class GeofenceData (val location : Location, context: Context) {

    var geoFenceList : List<Geofence> = generateGeofence()

    var geoFenceRequest: GeofencingRequest = generateRequest()


    val geoFencePendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceTransitionsIntentService::class.java)
        PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun generateRequest(): GeofencingRequest {
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofences(geoFenceList)
        }.build()
    }

    private fun generateGeofence(): List<Geofence> {
        return listOf<Geofence>(
            Geofence.Builder()
                .setRequestId("work")
                .setCircularRegion(location.latitude, location.longitude, 150.0f)
                .setExpirationDuration(20000)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setLoiteringDelay(2000)
                .build()
        )
    }


}