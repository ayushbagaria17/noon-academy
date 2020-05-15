package com.ayush.noonacademy.di

import android.content.Context
import android.content.SharedPreferences
import com.ayush.noonacademy.utils.AppScope
import com.ayush.noonacademy.utils.SharedPreferencesUtil
import com.ayush.noonacademy.utils.SharedPreferencesUtilImpl
import dagger.Module
import dagger.Provides

const val AYUSH_SHARED_PREF = "AYUSH_SHARED_PREF"
@Module
object SharedPrefModule {

    @AppScope
    @Provides
    fun providesUserSharedPref(sharePref: SharedPreferences): SharedPreferencesUtil = SharedPreferencesUtilImpl(sharePref)

    @AppScope
    @Provides
    fun providesSharedPref(context: Context) = context.getSharedPreferences(AYUSH_SHARED_PREF, Context.MODE_PRIVATE)
}