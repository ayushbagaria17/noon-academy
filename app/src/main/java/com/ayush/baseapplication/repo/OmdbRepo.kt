package com.ayush.baseapplication.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.ayush.baseapplication.repo.db.OmdbDbRepo
import com.ayush.baseapplication.repo.domain.OmdbItem
import com.ayush.baseapplication.repo.domain.OmdbSearchResponse
import com.ayush.baseapplication.utils.rx.SchedulerProvider

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

    override fun runQuery(query: String): LiveData<Response> {
        getListFromServer(query)
        val databaseSource = dbRepo.searchQuery(query)
        mediator.addSource(databaseSource) {
            mediator.postValue(Response.Success(it?: listOf()))
        }
        mediator.addSource(errorLiveData) {
            mediator.postValue(Response.Error(it))
        }
        mediator.addSource(loadingLiveData) {
            mediator.postValue(Response.Loading(it))
        }
        return mediator
    }

    private fun getListFromServer(query: String) {
        omdbApi.getResuultForQuery(query)
            .doOnSubscribe { loadingLiveData.postValue(true) }
            .observeOn(schedulerProvider.io())
            .subscribeOn(schedulerProvider.io())
            .subscribe(this::next, this::error)//these are called from background thread
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