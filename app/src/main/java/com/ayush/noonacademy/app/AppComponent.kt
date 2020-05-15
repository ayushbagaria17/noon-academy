package com.ayush.noonacademy.app

import android.app.Application
import com.ayush.noonacademy.di.SharedPrefModule
import com.ayush.noonacademy.network.API_KEY
import com.ayush.noonacademy.network.BASE_URL
import com.ayush.noonacademy.network.NetworkModule
import com.ayush.noonacademy.repo.db.DbModule
import com.ayush.noonacademy.repo.db.OmdbDbRepo
import com.ayush.noonacademy.repo.network.OmdbNetworkServiceModule
import com.ayush.noonacademy.repo.network.OmdbService
import com.ayush.noonacademy.utils.AppScope
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named

@AppScope
@Component(modules = [AppModule::class, NetworkModule::class, SharedPrefModule::class, OmdbNetworkServiceModule::class, DbModule::class])
interface AppComponent : RepoComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance @Named(BASE_URL) baseUrl: String,
            @BindsInstance @Named(API_KEY) apiKey: String,
            @BindsInstance app: Application
        ): AppComponent
    }
}

interface RepoComponent{
    fun providesOmdbDbRepo(): OmdbDbRepo
    fun providesOmdbService(): OmdbService
}
