package com.bradie.app.repository.networkbound

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bradie.app.apiservice.Api
import com.bradie.app.apiservice.ImagesModel
import com.bradie.app.utilstest.mock
import com.bradie.app.utils.DEFAULT_TRENDING_QUERY
import com.bradie.app.utils.FakeModel
import com.bradie.app.utils.ViewStatus
import io.reactivex.Flowable
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class RepoPixabayNetworkTest {
    private val api = mock<Api>()
    private val repo = RepoPixabayNetwork(api = api)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `test success call`() {
        val hits = FakeModel.createImagesData(count = 10)
        val imageModel = ImagesModel(hits = hits)
        val apiData = Flowable.just(imageModel)

        /**
         * set the data to be returned when api is called.
         * In this case, it is the apiData which was created from fakeModel.
         */
        `when`(api.getImages(DEFAULT_TRENDING_QUERY)).thenReturn(apiData)
        val observer = mock<Observer<ViewStatus<ImagesModel>>>()
        repo.loadImage(DEFAULT_TRENDING_QUERY).observeForever(observer)

        /**
         * Verify that data returned by the `repo.loadImage()` is same as
         * the data that was set to api call.
         */
        verify(api, only()).getImages(DEFAULT_TRENDING_QUERY)
    }
}
