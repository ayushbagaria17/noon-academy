package com.ayush.baseapplication.app

import com.ayush.baseapplication.di.SharedPrefModule
import com.ayush.baseapplication.network.BASE_URL
import com.ayush.baseapplication.network.NetworkModule
import com.ayush.baseapplication.utils.AppScope
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named

@AppScope
@Component(modules = [AppModule::class, NetworkModule::class, SharedPrefModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance @Named(BASE_URL) baseUrl: String
        ): AppComponent
    }
}