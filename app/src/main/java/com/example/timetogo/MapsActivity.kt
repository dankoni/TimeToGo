package com.example.timetogo

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.timetogo.geofence.GeofenceTransitionsIntentService
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

private const val LOCATION_PERMISSION_REQUEST_CODE = 1

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMyLocationButtonClickListener, EasyPermissions.PermissionCallbacks  {
    val TAG = "MapActivity"


    private var showPermissionDeniedDialog: Boolean  = false
    var geoFenceList : List<Geofence> = ArrayList()

    lateinit var geofencingClient: GeofencingClient
    lateinit var map: GoogleMap

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, GeofenceTransitionsIntentService::class.java)
        PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        geofencingClient = LocationServices.getGeofencingClient(this)

        val mapFragment : SupportMapFragment? =
            supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return

        map = googleMap

        enableMyLocation()

        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(LOCATION_PERMISSION_REQUEST_CODE)
    private fun enableMyLocation() {
        // Enable the location layer. Request the location permission if needed.
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

        if (EasyPermissions.hasPermissions(this, *permissions)) {
            map.isMyLocationEnabled = true

        } else {
            // if permissions are not currently granted, request permissions
            EasyPermissions.requestPermissions(this,
                getString(R.string.permission_rationale_location),
                LOCATION_PERMISSION_REQUEST_CODE, *permissions)
        }
    }

    /**
     * Display a dialog box asking the user to grant permissions if they were denied
     */
    override fun onResumeFragments() {
        super.onResumeFragments()
        if (showPermissionDeniedDialog) {
            AlertDialog.Builder(this).apply {
                setPositiveButton(R.string.ok, null)
                setMessage(R.string.location_permission_denied)
                create()
            }.show()
            showPermissionDeniedDialog = false
        }
    }


    private fun getGeofencingRequest(): GeofencingRequest {
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofences(geoFenceList)
        }.build()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,
            permissions, grantResults, this)

    }

    @SuppressLint("MissingPermission")
    override fun onMyLocationClick(location: Location) {

        val geofence = Geofence.Builder()
            .setRequestId("work")
            .setCircularRegion(location.latitude,location.longitude,150.0f)
            .setExpirationDuration(20000)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setLoiteringDelay(2000)
            .build()

        geoFenceList = listOf(geofence)

        addGeofenceMarker(location)
        geofencingClient?.addGeofences(getGeofencingRequest(), geofencePendingIntent)?.run {
            addOnSuccessListener {
                // Geofences added
                // ...

                Log.e(TAG, "Event recived :succes")

            }
            addOnFailureListener {
                // Failed to add geofences
                // ...

                Log.e(TAG, "Event recived : fail")

            }
        }
    }

    private fun addGeofenceMarker(location:Location) {
        val latLng = LatLng(location.latitude,location.longitude)
        map.addMarker(MarkerOptions().position(latLng).title("Work"))

        val fenceCircle = CircleOptions().center(latLng).radius(150.0).fillColor(0x40ff0000).strokeColor(Color.TRANSPARENT).strokeWidth(2f)
        map.addCircle(fenceCircle)
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        showPermissionDeniedDialog = true
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }
}
