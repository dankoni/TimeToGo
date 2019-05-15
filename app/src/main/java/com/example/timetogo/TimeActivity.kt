package com.example.timetogo

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders
import com.example.timetogo.data.AppConfigDao
import com.example.timetogo.setup.MapsActivity

import kotlinx.android.synthetic.main.activity_time.*
import org.koin.android.ext.android.inject
import java.util.*

class TimeActivity : AppCompatActivity() {

    private val loactionRequestCode = 201
    private lateinit var deapartureTextView: TextView

    private val appConfig : SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_time)
        setSupportActionBar(toolbar)

        if(appConfig.getBoolean("isFirstLaunch",true)){
            val intent = Intent(this, MapsActivity::class.java)
            startActivityForResult(intent,loactionRequestCode)
        }

        deapartureTextView = findViewById(R.id.departure_time)


        val date = Calendar.getInstance().get(Calendar.HOUR).plus(8)
        deapartureTextView.setText("Depaerture time: ${date}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val date = Calendar.getInstance().get(Calendar.HOUR).plus(8)
        deapartureTextView.setText("Depaerture time: ${date}")

        if(resultCode == Activity.RESULT_OK){
            val date = Calendar.getInstance().get(Calendar.HOUR).plus(8)
            deapartureTextView.setText("Depaerture time: ${date}")
        }
    }





}
