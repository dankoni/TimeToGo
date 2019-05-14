package com.example.timetogo.data

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single {androidContext().getSharedPreferences("app", Context.MODE_PRIVATE)}

    single { AppDatabase.createDatabase(androidContext()) }

    factory { get<AppDatabase>().appConfigDao() }

}