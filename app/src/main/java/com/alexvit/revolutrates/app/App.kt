package com.alexvit.revolutrates.app

import android.app.Application
import com.alexvit.revolutrates.BuildConfig
import timber.log.Timber

class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().build()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}