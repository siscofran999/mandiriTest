package com.siscofran.mandiri.ui.reviews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.siscofran.mandiri.data.ApiRepository
import com.siscofran.mandiri.data.model.Result
import com.siscofran.mandiri.data.model.ResultY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReviewsViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val error = MutableLiveData<String>()
    private val reviews = MutableLiveData<ArrayList<ResultY>>()
    private val page = MutableLiveData<Int>()
    private val loadMore = MutableLiveData<ArrayList<ResultY>>()

    fun getReviews(movieId: Int) {
        page.value = 1
        compositeDisposable.add(apiRepository.getReviews(movieId, page.value?.toInt()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                reviews.value = it.results
            },{
                error.value = it.message
            }))
    }

    fun loadMore(movieId: Int) {
        page.value = page.value?.plus(1)
        compositeDisposable.add(apiRepository.getReviews(movieId, page.value?.toInt()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loadMore.value = it.results
            },{
                error.value = it.message
            }))
    }

    fun error() = error
    fun review() = reviews
    fun loadMoreReviews() = loadMore

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}