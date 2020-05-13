package com.ayush.baseapplication.repo.network

import com.ayush.baseapplication.network.API_KEY
import com.ayush.baseapplication.utils.AppScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
object OmdbNetworkServiceModule {

    @Provides
    @AppScope
    fun provideOmdbApi(retrofit: Retrofit) : OmdbApi = retrofit.create(OmdbApi::class.java)

    @Provides
    @AppScope
    fun provideOmdbService(omdbApi: OmdbApi, @Named(API_KEY) apiKey: String): OmdbService = OmdbServiceImpl(apiKey, omdbApi)
}