package com.example.timetogo

import android.app.Application
import com.example.timetogo.data.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TimeApp: Application() {

    override fun onCreate(){
        super.onCreate()

        startKoin{
            androidContext(this@TimeApp)
            modules(appModule, dataModule)
        }
    }

}