package com.bradie.app.view.fragments.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.bradie.app.repository.networkbound.RepoPixabayNetwork
import com.bradie.app.utils.DEFAULT_QUERY
import kotlinx.coroutines.Dispatchers.IO

class HomeViewModel
@ViewModelInject
constructor(private val repoPixabayNetwork: RepoPixabayNetwork): ViewModel() {

    private val _query: MutableLiveData<String> = MutableLiveData("")
    val query: LiveData<String>
        get() = _query

    fun loadData(query: String) {
        if (query != _query.value) {
            _query.postValue(query)
        }
    }

    val data = _query.switchMap {
        liveData(IO) {
            emitSource(repoPixabayNetwork.loadImage(query = _query.value ?: DEFAULT_QUERY))
        }
    }
}