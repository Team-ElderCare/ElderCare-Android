package com.example.eldercare

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.eldercare.util.ElderCareDebugTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ElderCareApp : Application() {
    override fun onCreate() {
        super.onCreate()

        setTimber()
        setDarkMode()
    }

    private fun setTimber() {
        if (BuildConfig.DEBUG) Timber.plant(ElderCareDebugTree())
    }

    private fun setDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
