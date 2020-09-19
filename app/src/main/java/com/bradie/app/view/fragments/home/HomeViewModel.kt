package com.bradie.app.view.fragments.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.bradie.app.apiservice.ImagesModel
import com.bradie.app.repository.networkbound.RepoPixabayNetwork
import com.bradie.app.utils.DEFAULT_QUERY
import com.bradie.app.utils.ViewStatus
import kotlinx.coroutines.Dispatchers.IO

class HomeViewModel
@ViewModelInject
constructor(private val repoPixabayNetwork: RepoPixabayNetwork): ViewModel() {

    private var _viewStatus: LiveData<ViewStatus<ImagesModel>> = MutableLiveData()

    private val _query: MutableLiveData<String> = MutableLiveData("")
    val query: LiveData<String>
        get() = _query

    val viewStatus: LiveData<ViewStatus<ImagesModel>>
        get() = _viewStatus

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