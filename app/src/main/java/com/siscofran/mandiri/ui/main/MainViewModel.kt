package com.siscofran.mandiri.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.siscofran.mandiri.data.ApiRepository
import com.siscofran.mandiri.data.model.GenreX
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val error = MutableLiveData<String>()
    private val genre = MutableLiveData<List<GenreX>>()

    fun getGenre() {
        compositeDisposable.add(apiRepository.getGenre()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                genre.value = it.genres
            },{
                error.value = it.message
            }))
    }

    fun error() = error
    fun genre() = genre

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}