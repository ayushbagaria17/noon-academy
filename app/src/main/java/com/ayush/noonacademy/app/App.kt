package com.ayush.noonacademy.app

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.ayush.noonacademy.BuildConfig

class App : MultiDexApplication() {
    private val baseUrl = BuildConfig.BASE_URL
    private val apiKey = BuildConfig.API_KEY

    private val component by lazy(LazyThreadSafetyMode.NONE) {
        DaggerAppComponent.factory()
            .create(baseUrl, apiKey, this)
    }

    fun getAppComponent(): AppComponent {
        return component
    }

    companion object {
        fun get(context: Context): App = context.applicationContext as App
    }
}