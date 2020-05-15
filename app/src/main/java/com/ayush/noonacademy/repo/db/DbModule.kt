package com.ayush.noonacademy.repo.db

import android.content.Context
import com.ayush.noonacademy.utils.AppScope
import dagger.Module
import dagger.Provides


@Module
object DbModule {
    @Provides
    @AppScope
    fun provideDatabase(context: Context): OmdbDb = OmdbDb.create(context)

    @Provides
    @AppScope
    fun provideDatabaseRepo(omdbDb: OmdbDb): OmdbDbRepo = OmdbDbRepoImpl(omdbDb)
}