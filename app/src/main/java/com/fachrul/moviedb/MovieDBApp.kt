package com.fachrul.moviedb

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieDBApp:Application(){
    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler{ thread, throwable->
            Log.e("Apps Error", "Error System", throwable)
        }
    }
}