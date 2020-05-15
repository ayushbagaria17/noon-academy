package com.ayush.noonacademy.app

import android.app.Application
import android.content.Context
import com.ayush.noonacademy.utils.AppScope
import dagger.Module
import dagger.Provides

@Module
object AppModule {

    @AppScope
    @Provides
    fun provideAppContext(app: Application) : Context = app
}