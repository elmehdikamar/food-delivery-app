package com.mehdikamar.fooddelivery.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.mehdikamar.fooddelivery.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideApiUrl(): String = BuildConfig.API_ENDPOINT

    @Singleton
    @Provides
    fun provideOkHttpClient(
        chuckerInterceptor: ChuckerInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .followRedirects(false)
            .followSslRedirects(false)
            .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        }

        return builder.build()
    }

    @Singleton
    @Provides
    fun provideNetworkService(
        okHttpClient: OkHttpClient,
        apiUrl: String
    ): Retrofit = Retrofit.Builder()
        .baseUrl(apiUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    @Singleton
    @Provides
    fun provideChuckerInterceptor(context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context.applicationContext)
            .collector(ChuckerCollector(context.applicationContext))
            .redactHeaders(emptySet())
            .build()
    }

    private companion object {
        const val HTTP_CONNECT_TIMEOUT = 60L
        const val HTTP_WRITE_TIMEOUT = 120L
        const val HTTP_READ_TIMEOUT = 60L
    }
}