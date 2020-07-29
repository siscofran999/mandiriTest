package com.siscofran.mandiri.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siscofran.mandiri.data.ApiRepository
import com.siscofran.mandiri.data.model.Detail
import com.siscofran.mandiri.data.model.Reviews
import kotlinx.coroutines.launch
import javax.inject.Inject


class DetailViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    private val error = MutableLiveData<String>()
    private val detail = MutableLiveData<Detail>()
    private val video = MutableLiveData<String>()
    private val reviews = MutableLiveData<Reviews>()

    fun getDetail(movieId: Int) {
        viewModelScope.launch {
            runCatching {
                detail.value = apiRepository.getDetail(movieId)
            }.onFailure(::handleFailure)
        }
    }

    fun getVideo(movieId: Int) {
        viewModelScope.launch {
            runCatching {
                video.value = apiRepository.getVideos(movieId).results[0].key
            }.onFailure(::handleFailure)
        }
    }

    fun getReview(movieId: Int) {
        viewModelScope.launch {
            runCatching {
                reviews.value = apiRepository.getReviews(movieId, 1)
            }.onFailure(::handleFailure)
        }
    }

    private fun handleFailure(throwable: Throwable) {
        error.value = throwable.message
    }

    fun detail() = detail
    fun error() = error
    fun video() = video
    fun review() = reviews

}