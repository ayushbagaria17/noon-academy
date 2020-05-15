package com.ayush.noonacademy.network

import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

internal const val CONNECTION_TIMEOUT = 60_000L
internal const val READ_TIMEOUT = 60_000L
internal const val WRITE_TIMEOUT = 60_000L
@Module
object HttpModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun okHttpClientProvider(connectionPool: ConnectionPool, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val client =  OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(loggingInterceptor)
            .connectionPool(connectionPool)
        return client.build()
    }

    @Provides
    fun provideConnectionPool(): ConnectionPool = ConnectionPool()


}