package com.example.timetogo.setup

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.timetogo.R
import com.example.timetogo.TimeActivity
import com.example.timetogo.TimeViewModel
import com.example.timetogo.data.AppConfig
import com.example.timetogo.data.AppConfigDao
import com.example.timetogo.geofence.MapData
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import org.koin.android.ext.android.inject
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

private const val LOCATION_PERMISSION_REQUEST_CODE = 1

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMyLocationButtonClickListener, EasyPermissions.PermissionCallbacks  {
    private lateinit var viewModel: TimeViewModel
    val TAG = "MapActivity"

    private var showPermissionDeniedDialog: Boolean  = false

    private val appConfig : SharedPreferences by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TimeViewModel::class.java)

        viewModel.isGeofenceAdded().observe(this, Observer<Boolean> { whenGeoFenceIsAdded() })

        setContentView(R.layout.activity_main)

        val mapFragment : SupportMapFragment? =
            supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

    }

    private fun whenGeoFenceIsAdded() {
           appConfig.edit().putBoolean("isFirstLaunch",false).commit()
           val intent = Intent(this, TimeActivity::class.java)
           startActivity(intent)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return

        viewModel.whenMapIsReady(googleMap)

        enableMyLocation()

        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(LOCATION_PERMISSION_REQUEST_CODE)
    private fun enableMyLocation() {
        // Enable the location layer. Request the location permission if needed.
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

        if (EasyPermissions.hasPermissions(this, *permissions)) {
            viewModel.map.isMyLocationEnabled = true

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
        viewModel.setGeofence(location,this)
        viewModel.drawMarker(location)
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
