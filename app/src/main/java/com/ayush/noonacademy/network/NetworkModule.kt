package com.ayush.noonacademy.network

import com.ayush.noonacademy.utils.AppScope
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

const val BASE_URL = "BASE_URL"
const val API_KEY = "API_KEY"
@Module(includes = [GsonModule::class, HttpModule::class])
object NetworkModule {
    @Provides
    @AppScope
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    @Provides
    @AppScope
    fun provideRetrofit(
        gson: Gson,
        client: OkHttpClient,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        @Named(BASE_URL) baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .build()
    }
}