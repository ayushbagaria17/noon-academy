package com.ayush.noonacademy.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides

@Module
object GsonModule {

    @Provides
    fun provideGson() : Gson  {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }
}