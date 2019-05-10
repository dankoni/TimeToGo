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


              // Send notification and log the transition details.
              addEventToCalendar()
          } else {
            // Log the error.

        }
    }

    private fun addEventToCalendar() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR,8)

        val starttime = calendar
        starttime.add(Calendar.HOUR,8)

        val endtime = starttime
        endtime.add(Calendar.MINUTE, 30)
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, starttime)
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endtime)
            .putExtra(Events.TITLE, "Go Home")
            .putExtra(Events.EVENT_LOCATION, "The wokr")
            .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent)

    }
}

