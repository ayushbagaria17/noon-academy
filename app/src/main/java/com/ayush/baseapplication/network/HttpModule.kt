package com.ayush.baseapplication.network

import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

internal const val CONNECTION_TIMEOUT = 60_000L
internal const val READ_TIMEOUT = 60_000L
internal const val WRITE_TIMEOUT = 60_000L
@Module
object HttpModule {

    @Provides
    fun okHttpClientProvider(connectionPool: ConnectionPool): OkHttpClient {
        val client =  OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectionPool(connectionPool)
        return client.build()
    }

    @Provides
    fun provideConnectionPool(): ConnectionPool = ConnectionPool()


}