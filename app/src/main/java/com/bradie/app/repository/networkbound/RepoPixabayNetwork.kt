package com.bradie.app.repository.networkbound

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.bradie.app.apiservice.Api
import com.bradie.app.apiservice.ImagesModel
import com.bradie.app.extensions.toLiveData
import com.bradie.app.utils.ViewStatus
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.pow

class RepoPixabayNetwork
@Inject constructor(private val api: Api) {

    fun loadImage(query: String): LiveData<ViewStatus<ImagesModel>> {
        val imageData: MediatorLiveData<ViewStatus<ImagesModel>> = MediatorLiveData()
        imageData.postValue(
            ViewStatus.loading(
                "Loading",
                null
            )
        )
        val source = api.getImages(
            query = query
        )
            .subscribeOn(Schedulers.io())
            .retryWhen { errors ->
                errors.zipWith(
                    Flowable.range(1, 4),
                    { error: Throwable, retryCount: Int ->
                        if (retryCount > 3) {
                            throw error
                        } else {
                            imageData.postValue(
                                ViewStatus.loading(
                                    "Failed to Fetch. Retrying again...$retryCount of 3",
                                    null
                                )
                            )
                            retryCount
                        }
                    }
                ).flatMap { retryCount: Int ->
                    Flowable.timer(
                        2.0.pow(retryCount.toDouble()).toLong(),
                        TimeUnit.SECONDS
                    )
                }
            }
            .map {
                if (it.hits.isNullOrEmpty()) {
                    ViewStatus.error(msg = "No result found. Try again with different query.")
                } else {
                    ViewStatus.success(it)
                }
            }
            .doOnError {
                ViewStatus.error(msg = it.message, data = null)
            }
            .onErrorReturn {
                ViewStatus.error(msg = it.message)
            }
            .toLiveData()

        imageData.addSource(source) {
            imageData.value = it
            imageData.removeSource(source)
        }
        return imageData
    }
}
