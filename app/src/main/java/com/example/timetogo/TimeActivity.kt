package com.example.timetogo

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders
import com.example.timetogo.setup.MapsActivity

import kotlinx.android.synthetic.main.activity_time.*

class TimeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewModel = ViewModelProviders.of(this).get(TimeViewModel::class.java)

        setContentView(R.layout.activity_time)
        setSupportActionBar(toolbar)

        if(!viewModel.isFirstLanched){
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }


    }

}