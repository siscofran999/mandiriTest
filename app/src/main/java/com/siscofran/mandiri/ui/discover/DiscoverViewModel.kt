package com.siscofran.mandiri.ui.discover

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siscofran.mandiri.data.ApiRepository
import com.siscofran.mandiri.data.model.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class DiscoverViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    private val error = MutableLiveData<String>()
    private val discover = MutableLiveData<ArrayList<Result>>()
    private val page = MutableLiveData<Int>()
    private val loadMore = MutableLiveData<ArrayList<Result>>()

    fun getDiscover(idGenre: Int) {
        page.value = 1

        viewModelScope.launch {
            runCatching {
                discover.value = apiRepository.getDiscover(idGenre, page.value?.toInt()!!).results
            }.onFailure(::handleFailure)
        }
    }

    fun loadMore(idGenre: Int) {
        page.value = page.value?.plus(1)
        viewModelScope.launch {
            runCatching {
                loadMore.value = apiRepository.getDiscover(idGenre, page.value?.toInt()!!).results
            }.onFailure(::handleFailure)
        }
    }

    private fun handleFailure(throwable: Throwable) {
        error.value = throwable.message
    }

    fun error() = error
    fun discover() = discover
    fun loadMoreDiscover() = loadMore

}