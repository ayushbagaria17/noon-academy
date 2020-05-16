package com.ayush.noonacademy.ui

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ayush.noonacademy.base.BaseViewModel
import com.ayush.noonacademy.repo.OmdbRepo
import com.ayush.noonacademy.repo.Response
import com.ayush.noonacademy.repo.domain.OmdbItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchViewModel(private val omdbRepo: OmdbRepo) : BaseViewModel() {
    val searchSubject = PublishSubject.create<String>()
    private var profileResult: LiveData<Response> = omdbRepo.runQuery("")
    private var listOfItems = MutableLiveData<List<OmdbItem>>()
    val isEmptyViewVisible = ObservableInt(View.GONE)
    val isListVisible = ObservableInt(View.GONE)

    init {
        observeQueryChanges()
    }

    fun responseLiveData(): LiveData<Response> = profileResult
    fun listLiveData(): LiveData<List<OmdbItem>> = listOfItems


    @SuppressLint("CheckResult")
    private fun observeQueryChanges() {
        searchSubject
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                isListVisible.set(View.GONE)
                isEmptyViewVisible.set(View.GONE)
                profileResult = omdbRepo.runQuery(it)
            }
    }

    fun searchTextChanged(value: CharSequence, start: Int, before: Int, count: Int) {
        if (value.isNotEmpty()) {
            searchSubject.onNext(value.toString())
        }
    }

    fun handleData(response: Response) {
        if (response is Response.Success) {
            isListVisible.set(View.VISIBLE)
            isEmptyViewVisible.set(View.GONE)
            listOfItems.value = response.list
            return
        } else if (response is Response.Error && isEmptyViewVisible.get() == View.VISIBLE) {
            isListVisible.set(View.GONE)
            isEmptyViewVisible.set(View.GONE)
            listOfItems.value = emptyList()
        } else if (response is Response.Loading && response.isLoading && isListVisible.get() != View.VISIBLE) {
            isEmptyViewVisible.set(View.VISIBLE)
            listOfItems.value = emptyList()
        } else if (response is Response.Loading && !response.isLoading) {
            isEmptyViewVisible.set(View.GONE)
        }
    }


}