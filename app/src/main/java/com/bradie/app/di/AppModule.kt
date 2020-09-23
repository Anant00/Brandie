package com.bradie.app.di

import com.bradie.app.apiservice.Api
import com.bradie.app.repository.networkbound.RepoPixabayNetwork
import com.bradie.app.utils.BASE_URL
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.InstallIn
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

/**
 * This class is used by hilt to inject dependencies.
 * It should be annotated with @Module so that hilt knows which class
 * specifies dependencies to be injected.
 *
 * Each method must be annotated with @Provides.
 * IF a method is annotated with @Singleton, this method will have only single instance
 * of the dependencies throughout the scope.
 */
@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    /**
     * Provide Api class dependency. This would be inject in class REPO: the class
     * which handles fetching of data from network.
     */
    @Provides
    @Singleton
    fun provideApiService(client: OkHttpClient): Api {
        val newClient = with(client.newBuilder()) {
            /**
             * Setting read and write timeout. Slow internet may cause failure.
             */
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

    /**
     * OkHttpClient for debugging network response. This client
     * should be added in Retrofit builder.
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    /**
     * This provides dependency for Repo.
     */
    @Singleton
    @Provides
    fun providePixabayRepo(api: Api): RepoPixabayNetwork {
        return RepoPixabayNetwork(api = api)
    }
}
