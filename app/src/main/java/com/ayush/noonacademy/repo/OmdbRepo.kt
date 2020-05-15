package com.ayush.noonacademy.repo

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ayush.noonacademy.repo.db.OmdbDbRepo
import com.ayush.noonacademy.repo.domain.OmdbItem
import com.ayush.noonacademy.repo.domain.OmdbSearchResponse
import com.ayush.noonacademy.repo.network.OmdbService
import com.ayush.noonacademy.utils.rx.SchedulerProvider

sealed class Response {
    data class Success(val list: List<OmdbItem>): Response()
    data class Error(val error: String): Response()
    data class Loading(val  isLoading: Boolean): Response()
}

class OmdbRepoImpl(
    private val omdbApi: OmdbService,
    private  val dbRepo: OmdbDbRepo,
    private val schedulerProvider: SchedulerProvider
) : OmdbRepo {

    private val mediator = MediatorLiveData<Response>()
    private val errorLiveData = MutableLiveData<String>()
    private val loadingLiveData = MutableLiveData<Boolean>()
    val dbOserver: Observer<List<OmdbItem>> =
        Observer<List<OmdbItem>> { list ->
            if (list != null && list.isNotEmpty()) {
                mediator.postValue(Response.Success(list))
            }
        }
    val errorObserver: Observer<String> =
        Observer { error ->
            mediator.postValue(Response.Error(error))
        }

    private val loadingObserver: Observer<Boolean> =
        Observer { isLoading ->
            mediator.postValue(Response.Loading(isLoading))
        }


    override fun runQuery(query: String): LiveData<Response> {
        getListFromServer(query)
        val databaseSource = dbRepo.searchQuery(query)
        mediator.addSource(databaseSource, dbOserver)
        mediator.addSource(errorLiveData, errorObserver)
        mediator.addSource(loadingLiveData, loadingObserver)
        return mediator
    }

    @SuppressLint("CheckResult")
    private fun getListFromServer(query: String) {
        omdbApi.getResultForQuery(query)
            .doOnSubscribe {
                loadingLiveData.postValue(true)
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.io())
            .subscribe(this::next, this::error)
    }

    private fun next(response : OmdbSearchResponse) {
        loadingLiveData.postValue(false)
        dbRepo.insertResultIntoDb(response)
    }

    private fun error(error: Throwable) {
        loadingLiveData.postValue(false)
        errorLiveData.postValue(error.message ?: "network_error")
    }

}

interface OmdbRepo {
    fun runQuery(query: String):  LiveData<Response>
}