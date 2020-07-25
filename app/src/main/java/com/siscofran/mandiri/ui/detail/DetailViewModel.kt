package com.siscofran.mandiri.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.siscofran.mandiri.data.ApiRepository
import com.siscofran.mandiri.data.model.Detail
import com.siscofran.mandiri.data.model.ResultY
import com.siscofran.mandiri.data.model.Reviews
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val error = MutableLiveData<String>()
    private val detail = MutableLiveData<Detail>()
    private val video = MutableLiveData<String>()
    private val reviews = MutableLiveData<Reviews>()

    fun getDetail(movieId: Int) {
        compositeDisposable.add(apiRepository.getDetail(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                detail.value = it
            },{
                error.value = it.message
            }))
    }

    fun getVideo(movieId: Int) {
        compositeDisposable.add(apiRepository.getVideos(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.results.isNotEmpty()){
                    video.value = it.results[0].key
                }else{
                    video.value = ""
                }
            },{
                error.value = it.message
            }))
    }

    fun getReview(movieId: Int) {
        compositeDisposable.add(apiRepository.getReviews(movieId, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                reviews.value = it
            },{
                error.value = it.message
            }))
    }

    fun detail() = detail
    fun error() = error
    fun video() = video
    fun review() = reviews

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}