package com.example.timetogo.geofence

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.timetogo.R
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceTransitionsIntentService : IntentService("GeoCalendarService") {

    val TAG = "GeoCalendarService"

    override fun onHandleIntent(intent: Intent?) {

        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceErrorMessages.getErrorString(this,
                geofencingEvent.errorCode)
            Log.e(TAG, errorMessage)
            return
        }

        // Get the transition type.
        val geofenceTransition = geofencingEvent.geofenceTransition

        /*  // Test that the reported transition was of interest.
          if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER |
          geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

              val triggeringGeofences = geofencingEvent.triggeringGeofences

              // Get the transition details as a String.
              val geofenceTransitionDetails = getGeofenceTransitionDetails(
                  this,
                  geofenceTransition,
                  triggeringGeofences
              )

              // Send notification and log the transition details.
              sendNotification(geofenceTransitionDetails)
              Log.i(TAG, geofenceTransitionDetails)
          } else {
              // Log the error.
              Log.e(TAG, getString(
                  R.string.geofence_transition_invalid_type,
                  geofenceTransition))
          }*/
    }
}

