package com.ayush.baseapplication.app

import android.app.Application
import android.content.Context
import com.ayush.baseapplication.utils.AppScope
import dagger.Module
import dagger.Provides

@Module
object AppModule {

    @AppScope
    @Provides
    fun provideAppContext(app: Application) : Context = app
}