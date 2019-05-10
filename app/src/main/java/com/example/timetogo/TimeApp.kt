package com.example.timetogo

import android.app.Application
import org.koin.android.ext.android.startKoin

class TimeApp: Application() {

    override fun onCreate(){
        super.onCreate()

        startKoin(this, timeComponent)
    }

}