package com.siscofran.mandiri.ui.reviews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siscofran.mandiri.data.ApiRepository
import com.siscofran.mandiri.data.model.ResultY
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReviewsViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    private val error = MutableLiveData<String>()
    private val reviews = MutableLiveData<ArrayList<ResultY>>()
    private val page = MutableLiveData<Int>()
    private val loadMore = MutableLiveData<ArrayList<ResultY>>()

    fun getReviews(movieId: Int) {
        page.value = 1
        viewModelScope.launch {
            runCatching {
                reviews.value = apiRepository.getReviews(movieId, page.value?.toInt()!!).results
            }.onFailure(::handleFailure)
        }
    }

    fun loadMore(movieId: Int) {
        page.value = page.value?.plus(1)
        viewModelScope.launch {
            runCatching {
                loadMore.value = apiRepository.getReviews(movieId, page.value?.toInt()!!).results
            }.onFailure(::handleFailure)
        }
    }

    private fun handleFailure(throwable: Throwable) {
        error.value = throwable.message
    }

    fun error() = error
    fun review() = reviews
    fun loadMoreReviews() = loadMore

}