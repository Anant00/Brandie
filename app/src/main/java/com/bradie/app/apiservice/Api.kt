package com.bradie.app.apiservice

import com.bradie.app.BuildConfig
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    /**
     * @param query is the query that has to be appended to the url.
     * The api would fetch data pertaining to that query.
     * @see {https://pixabay.com/api/?key=ApiKeyHere&q=Models}: This is the
     * format of the url.
     */
    @GET("?key=${BuildConfig.ApiKey}")
    fun getImages(
        @Query("q") query: String
    ): Flowable<ImagesModel>
}
