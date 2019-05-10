package com.example.timetogo.geofence

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.timetogo.R
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import android.provider.CalendarContract.Events
import android.provider.CalendarContract
import com.example.timetogo.TimeActivity
import java.util.*


class GeofenceTransitionsIntentService : IntentService("GeoCalendarService") {

    val TAG = "GeoCalendarService"

    override fun onHandleIntent(intent: Intent?) {

        val geofencingEvent = GeofencingEvent.fromIntent(intent)

        Log.e(TAG, "Event recived : ${geofencingEvent.geofenceTransition}")


        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceErrorMessages.getErrorString(this,
                geofencingEvent.errorCode)
            Log.e(TAG, errorMessage)
            return
        }

        // Get the transition type.
        val geofenceTransition = geofencingEvent.geofenceTransition

          // Test that the reported transition was of interest.
          if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ) {

              val triggeringGeofences = geofencingEvent.triggeringGeofences


              setTrigger()
          } else {
            // Log the error.

        }
    }

    private fun setTrigger() {


    }


}

