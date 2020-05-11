package com.ayush.baseapplication.app

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.ayush.baseapplication.BuildConfig

class App : MultiDexApplication() {
    private val baseUsrl = BuildConfig.BASE_URL

    private val component by lazy(LazyThreadSafetyMode.NONE) {
        DaggerAppComponent.factory()
            .create(baseUsrl)
    }

    fun getAppComponent(): AppComponent {
        return component
    }

    companion object {
        fun get(context: Context): App = context.applicationContext as App
    }
}