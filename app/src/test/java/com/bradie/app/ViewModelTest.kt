package com.bradie.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bradie.app.repository.networkbound.RepoPixabayNetwork
import com.bradie.app.view.fragments.home.HomeViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private val testDispatchers = TestCoroutineDispatcher()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var mainViewModel: HomeViewModel
    private lateinit var networkRepo: RepoPixabayNetwork

    @Before
    fun setUp() {
        networkRepo = mock(RepoPixabayNetwork::class.java)
        mainViewModel = HomeViewModel(networkRepo, testDispatchers)
    }


    @Test
    fun `is viewModel emitting the value as soon as we create the object of it`() {
        val observer = mock<Observer<String>>()
        verifyZeroInteractions(observer)
        mainViewModel.setQuery("dogs")
        mainViewModel.query.observeForever(observer)
        verify(observer).onChanged("dogs")
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun `check if query is updated on called only for UNIQUE value`() {
        val observer = mock<Observer<String>>()
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
    fun `check if load more data is called on each UNIQUE page increment`() {
        runBlocking {
            mainViewModel.dataOnMain.observeForever(mock())
            verify(networkRepo).loadImage(mainViewModel.query.value!!)
            reset(networkRepo)
            mainViewModel.setQuery(query = "puppies")
            verify(networkRepo).loadImage("puppies")
            mainViewModel.setQuery(query = "puppies")
            verifyZeroInteractions(networkRepo)
            mainViewModel.setQuery(query = "dogs")
            verify(networkRepo).loadImage("dogs")
            mainViewModel.setQuery(query = "dogs")
            verifyNoMoreInteractions(networkRepo)
        }
    }
}
