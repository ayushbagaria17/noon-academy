package com.ayush.baseapplication.repo.db

import android.content.Context
import androidx.room.Room
import com.ayush.baseapplication.utils.AppScope
import dagger.Module
import dagger.Provides


@Module
object DdModule {
    @Provides
    @AppScope
    fun provideDatabase(context: Context): OmdbDb = OmdbDb.create(context)
}