package com.bradie.app.apiservice

import com.bradie.app.BuildConfig
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("?key=${BuildConfig.ApiKey}")
    fun getImages(
        @Query("q") query: String
    ): Flowable<ImagesModel>
}