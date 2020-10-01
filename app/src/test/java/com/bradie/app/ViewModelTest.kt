package com.bradie.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bradie.app.apiservice.ImagesModel
import com.bradie.app.repository.networkbound.RepoPixabayNetwork
import com.bradie.app.utils.ViewStatus
import com.bradie.app.view.viewmodel.SharedViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var mainViewModel: SharedViewModel
    private lateinit var networkRepo: RepoPixabayNetwork

    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
        networkRepo = mock(RepoPixabayNetwork::class.java)
        mainViewModel = SharedViewModel(networkRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `is viewModel emitting the value as soon as we create the object of it`() {
        val observer = com.bradie.app.utilstest.mock<Observer<String>>()
        verifyZeroInteractions(observer)
        mainViewModel.setQuery("dogs")
        mainViewModel.query.observeForever(observer)
        verify(observer).onChanged("dogs")
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun `check if query is updated on called only for UNIQUE value`() {
        val observer = com.bradie.app.utilstest.mock<Observer<String>>()
        verifyZeroInteractions(observer)
        mainViewModel.setQuery(query = "cats")
        mainViewModel.query.observeForever(observer)
        verify(observer).onChanged("cats")
        /*
            should not update value ( postValue() ) if current value in viewModel is equal to
            previous page as it will call the loadMoreData again.
        */
        mainViewModel.setQuery(query = "cats")
        verifyNoMoreInteractions(observer)
        /*
          update the value again (if not equal to previous value)
         */
        mainViewModel.setQuery(query = "puppies")
        verify(observer).onChanged("puppies")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `check if load data is called on each UNIQUE query`() {
            val observer = com.bradie.app.utilstest.mock<Observer<ViewStatus<ImagesModel>>>()
            verifyZeroInteractions(networkRepo)
            mainViewModel.data.observeForever(observer)
            mainViewModel.setQuery("foo")
            verify(networkRepo).loadImage("foo")
            reset(networkRepo)
            mainViewModel.setQuery(query = "foo")
            verifyNoMoreInteractions(networkRepo)
            mainViewModel.setQuery(query = "bar")
            verify(networkRepo).loadImage("bar")
    }

    @Test
    fun `fetch and change while Observed`() {
        val observer = com.bradie.app.utilstest.mock<Observer<ViewStatus<ImagesModel>>>()
            mainViewModel.data.observeForever(observer)
            mainViewModel.setQuery("foo")
            mainViewModel.setQuery("bar")
            verify(networkRepo).loadImage("foo")
            verify(networkRepo).loadImage("bar")
    }
}
