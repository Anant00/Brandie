package com.bradie.app.view.fragments.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.liveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.bradie.app.repository.networkbound.RepoPixabayNetwork
import com.bradie.app.utils.DEFAULT_FOLLOWING_QUERY
import com.bradie.app.utils.DEFAULT_TRENDING_QUERY
import kotlinx.coroutines.launch

class HomeViewModel
@ViewModelInject
constructor(private val repoPixabayNetwork: RepoPixabayNetwork) : ViewModel() {
    private val _query: MutableLiveData<String> = MutableLiveData()
    val query: LiveData<String>
        get() = _query
    init {
        viewModelScope.launch {
            setQuery(DEFAULT_TRENDING_QUERY)
            setQuery(DEFAULT_FOLLOWING_QUERY)
        }
    }

    fun setQuery(query: String) {
        if (query != _query.value) {
            _query.value = query
        }
    }

    val data = _query.switchMap {
        liveData {
            emitSource(repoPixabayNetwork.loadImage(query = it))
        }
    }
}
