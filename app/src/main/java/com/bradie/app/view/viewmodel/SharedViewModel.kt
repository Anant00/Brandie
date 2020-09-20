package com.bradie.app.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.bradie.app.repository.networkbound.RepoPixabayNetwork
import com.bradie.app.utils.DEFAULT_FOLLOWING_QUERY
import com.bradie.app.utils.DEFAULT_TRENDING_QUERY
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SharedViewModel
@ViewModelInject
constructor(private val repo: RepoPixabayNetwork) : ViewModel() {

    private val _query: MutableLiveData<String> = MutableLiveData()
    val query: LiveData<String>
        get() = _query

    init {
        viewModelScope.launch {
            setQuery(query = DEFAULT_TRENDING_QUERY)
            setQuery(query = DEFAULT_FOLLOWING_QUERY)
        }
    }

    fun setQuery(query: String) {
        if (query != _query.value) {
            _query.value = query
        }
    }

    val data = _query.switchMap {
        repo.loadImage(query = it)
    }

    val defaultDataTrending = liveData(IO) {
            emitSource(repo.loadImage(query = DEFAULT_TRENDING_QUERY))
        }

    val defaultFollowingData = liveData(IO) {
        emitSource(repo.loadImage(query = DEFAULT_FOLLOWING_QUERY))
    }
}
