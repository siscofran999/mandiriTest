package com.siscofran.mandiri.ui.discover

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.siscofran.mandiri.data.ApiRepository
import com.siscofran.mandiri.data.model.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DiscoverViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val error = MutableLiveData<String>()
    private val discover = MutableLiveData<ArrayList<Result>>()
    private val page = MutableLiveData<Int>()
    private val loadMore = MutableLiveData<ArrayList<Result>>()

    fun getDiscover(idGenre: Int) {
        page.value = 1
        compositeDisposable.add(apiRepository.getDiscover(idGenre, page.value?.toInt()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                discover.value = it.results
            },{
                error.value = it.message
            }))
    }

    fun loadMore(idGenre: Int) {
        page.value = page.value?.plus(1)
        compositeDisposable.add(apiRepository.getDiscover(idGenre, page.value?.toInt()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loadMore.value = it.results
            },{
                error.value = it.message
            }))
    }

    fun error() = error
    fun discover() = discover
    fun loadMoreDiscover() = loadMore

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}