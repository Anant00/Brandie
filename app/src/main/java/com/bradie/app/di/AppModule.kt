package com.bradie.app.di

import com.bradie.app.apiservice.Api
import com.bradie.app.repository.networkbound.RepoPixabayNetwork
import com.bradie.app.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApiService(client: OkHttpClient): Api {
        val newClient = with(client.newBuilder()) {
            connectTimeout(1, TimeUnit.MINUTES)
            readTimeout(1, TimeUnit.MINUTES)
            writeTimeout(1, TimeUnit.MINUTES)
        }.build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(newClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun providePixabayRepo(api: Api): RepoPixabayNetwork {
        return RepoPixabayNetwork(api = api)
    }

    @Singleton
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

}