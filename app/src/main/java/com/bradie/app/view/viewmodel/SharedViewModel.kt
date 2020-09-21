package com.bradie.app.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.bradie.app.repository.networkbound.RepoPixabayNetwork
import com.bradie.app.utils.DEFAULT_FOLLOWING_QUERY
import com.bradie.app.utils.DEFAULT_TRENDING_QUERY
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

/**
 * @annotation viewModelInject is used to inject dependencies into the @constructor.
 * @param repo is provided by the @class AppModule.
 *
 * Avoid passing a context or a view in the @constructor. This will cause memory leak.
 * For more info on this,  @see <a href="https://developer.android.com/topic/libraries/architecture/viewmodel">ViewModel</a>
 */
class SharedViewModel
@ViewModelInject
constructor(private val repo: RepoPixabayNetwork) : ViewModel() {

    /**
     * Keep the mutable variables private as these should not be changed by outside class.
     * The public variables should be immutable. They should only be used for reading / observing
     * the value.
     */
    private val _query: MutableLiveData<String> = MutableLiveData()
    val query: LiveData<String>
        get() = _query

    /**
     * init block is called only on initialization. This will be called only once if the
     * viewModel is injected properly using proper activityViewModel or viewModels.
     * This is a good place to initialize default data that should be presented to the user
     * as soon as the viewModel is injected.
     */

    init {
        viewModelScope.launch {
            setQuery(query = DEFAULT_TRENDING_QUERY)
            setQuery(query = DEFAULT_FOLLOWING_QUERY)
        }
    }

    /**
     * This method sets the value of @param query to the @param _query, which is a type of live data.
     * Set the new query value to the @param _query only if it is unique or different from the one
     * that was set earlier. This would prevent calling the api again and again of same query.
     */
    fun setQuery(query: String) {
        if (query != _query.value) {
            _query.value = query
        }
    }

    /**
     * As soon as the the of @param _query changes, .switchMap will be triggered and call the
     * `repo.loadImage()`. The variable data then can be observed on UI.
     */
    val data = _query.switchMap {
        repo.loadImage(query = it)
    }

    /**
     * This is used to observer the DEFAULT value.
     * The code block inside the LiveData(IO) runs in a separate background thread because Of the
     * passed IO in the constructor.
     * emitSource() returns the value wrapped in liveData.
     */
    val defaultDataTrending = liveData(IO) {
            emitSource(repo.loadImage(query = DEFAULT_TRENDING_QUERY))
        }

    val defaultFollowingData = liveData(IO) {
        emitSource(repo.loadImage(query = DEFAULT_FOLLOWING_QUERY))
    }
}
