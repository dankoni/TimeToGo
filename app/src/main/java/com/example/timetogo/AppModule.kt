package com.example.timetogo


import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel{ TimeViewModel(get())}
}