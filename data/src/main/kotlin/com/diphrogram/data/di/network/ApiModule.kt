package com.diphrogram.data.di.network

import com.diphrogram.data.BuildConfig
import com.diphrogram.domain.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ApiModule {

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .readTimeout(HALF_MINUTE, TimeUnit.SECONDS)
            .connectTimeout(HALF_MINUTE, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(
        client: OkHttpClient
    ): Api {
        val baseUrl = BuildConfig.BASE_URL

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(Api::class.java)
    }
}

private const val HALF_MINUTE = 30L