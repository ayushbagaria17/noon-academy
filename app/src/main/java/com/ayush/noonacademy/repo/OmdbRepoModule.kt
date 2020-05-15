package com.ayush.noonacademy.repo

import com.ayush.noonacademy.utils.ActivityScope
import com.ayush.noonacademy.repo.db.OmdbDbRepo
import com.ayush.noonacademy.repo.network.OmdbService
import com.ayush.noonacademy.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides


@Module
object OmdbRepoModule {

    @Provides
    @ActivityScope
    fun providesOmdbRepo(
        omdbApi: OmdbService,
        dbRepo: OmdbDbRepo,
        schedulerProvider: SchedulerProvider
    ): OmdbRepo = OmdbRepoImpl(omdbApi, dbRepo, schedulerProvider)
}