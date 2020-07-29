package com.siscofran.mandiri.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siscofran.mandiri.data.ApiRepository
import com.siscofran.mandiri.data.model.GenreX
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    private val error = MutableLiveData<String>()
    private val genre = MutableLiveData<List<GenreX>>()

    fun getGenre() {
        viewModelScope.launch {
            runCatching {
                genre.value = apiRepository.getGenre().genres
            }.onFailure(::handleFailure)
        }

    }

    private fun handleFailure(throwable: Throwable) {
        error.value = throwable.message
    }

    fun error() = error
    fun genre() = genre

}