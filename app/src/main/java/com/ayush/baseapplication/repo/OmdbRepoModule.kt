package com.ayush.baseapplication.repo

import com.ayush.baseapplication.repo.db.DdModule
import com.ayush.baseapplication.repo.db.OmdbDbRepo
import com.ayush.baseapplication.repo.network.OmdbNetworkServiceModule
import com.ayush.baseapplication.repo.network.OmdbService
import com.ayush.baseapplication.utils.ActivityScope
import com.ayush.baseapplication.utils.rx.SchedulerProvider
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