package com.example.timetogo

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders
import com.example.timetogo.data.AppConfigDao
import com.example.timetogo.setup.MapsActivity

import kotlinx.android.synthetic.main.activity_time.*
import org.koin.android.ext.android.inject

class TimeActivity : AppCompatActivity() {

    private val appConfig : SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_time)
        setSupportActionBar(toolbar)

        if(appConfig.getBoolean("isFirstLaunch",true)){
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }


    }



}
