package com.bradie.app.view.fragments.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.bradie.app.apiservice.ImagesModel
import com.bradie.app.repository.networkbound.RepoPixabayNetwork
import com.bradie.app.utils.DEFAULT_FOLLOWING_QUERY
import com.bradie.app.utils.DEFAULT_TRENDING_QUERY
import com.bradie.app.utils.ViewStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class HomeViewModel
@ViewModelInject
constructor(
    private val repoPixabayNetwork: RepoPixabayNetwork,
    private val dispatcher: CoroutineDispatcher
): ViewModel()
{
    private val _query: MutableLiveData<String> = MutableLiveData()
    val query: LiveData<String>
        get() = _query
    init {
        viewModelScope.launch(dispatcher) {
            setQuery(DEFAULT_TRENDING_QUERY)
            setQuery(DEFAULT_FOLLOWING_QUERY)
        }
    }

    fun setQuery(query: String) {
        if (query != _query.value) {
            _query.postValue(query)
        }
    }

    val data = _query.switchMap {
        liveData(dispatcher) {
            emitSource(repoPixabayNetwork.loadImage(query = it))
        }
    }

    val dataOnMain = _query.switchMap {
        repoPixabayNetwork.loadImage(query = it)
    }
}