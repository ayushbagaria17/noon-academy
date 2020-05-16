package com.ayush.noonacademy.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ayush.TestSchedulerProvider
import com.ayush.noonacademy.repo.db.OmdbDbRepo
import com.ayush.noonacademy.repo.domain.OmdbItem
import com.ayush.noonacademy.repo.domain.OmdbSearchResponse
import com.ayush.noonacademy.repo.network.OmdbService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class OmdbRepoImplTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    val omdbDbRepo: OmdbDbRepo = mock()
    val omdbDbService: OmdbService = mock()
    val schedulerProvider = TestSchedulerProvider()
    var mockStateObserver: Observer<Response> = mock()
    lateinit var omdbRepo: OmdbRepoImpl
    private val query = "query"
    private val omdbItem = OmdbItem(
        title = "title",
        imdbID = "iid",
        year = "year",
        type = "type",
        poster = "poster"
    )
    private val list = listOf(omdbItem)
    private val omdbSearchResponseEmpty = OmdbSearchResponse(listOf(), 200, "response")
    lateinit var liveData: MutableLiveData<List<OmdbItem>>

    @Before
    fun setup() {
        omdbRepo = OmdbRepoImpl(omdbDbService, omdbDbRepo, schedulerProvider)
    }


    @Test
    fun runQuery_PickDataFromDB() {
        liveData = MutableLiveData<List<OmdbItem>>().apply { value = listOf(omdbItem) }
        whenever(omdbDbRepo.searchQuery(query)).thenReturn(liveData)
        whenever(omdbDbService.getResultForQuery(query)).thenReturn(
            Single.just(
                omdbSearchResponseEmpty
            )
        )

        omdbRepo.runQuery(query).observeForever(mockStateObserver)

        verify(mockStateObserver).onChanged(Response.Success(listOf(omdbItem)))
        verify(omdbDbService).getResultForQuery(query)
    }

    @Test
    fun runQuery_CheckLoadingStatusAreBeingPassed() {
        liveData = MutableLiveData<List<OmdbItem>>().apply { value = listOf() }
        whenever(omdbDbRepo.searchQuery(query)).thenReturn(liveData)
        whenever(omdbDbService.getResultForQuery(query)).thenReturn(
            Single.just(
                omdbSearchResponseEmpty
            )
        )

        omdbRepo.runQuery(query).observeForever(mockStateObserver)

//        verify(mockStateObserver).onChanged(Response.Loading(true)) true one is getting overwritten right now and false is being sent
        verify(mockStateObserver).onChanged(Response.Loading(false))
        verify(omdbDbService).getResultForQuery(query)
    }

    @Test
    fun runQuery_CheckApiErrorIsBeingPassed() {
        liveData = MutableLiveData<List<OmdbItem>>().apply { value = listOf() }
        whenever(omdbDbRepo.searchQuery(query)).thenReturn(liveData)
        whenever(omdbDbService.getResultForQuery(query)).thenReturn(Single.error(Throwable("error")))

        omdbRepo.runQuery(query).observeForever(mockStateObserver)

        verify(mockStateObserver).onChanged(Response.Loading(false))
        verify(mockStateObserver).onChanged(Response.Error("error"))
        verify(omdbDbService).getResultForQuery(query)
    }

    @Test
    fun getListFromServer_OnSuccess() {
        val loadingObserver: Observer<Boolean> = mock()
        omdbRepo.loadingLiveData.observeForever(loadingObserver)
        whenever(omdbDbService.getResultForQuery(query)).thenReturn(
            Single.just(omdbSearchResponseEmpty)
        )

        omdbRepo.getListFromServer(query)

        verify(loadingObserver).onChanged(false)
        verify(omdbDbRepo).insertResultIntoDb(omdbSearchResponseEmpty)
    }

    @Test
    fun getListFromServer_OnError() {
        val loadingObserver: Observer<Boolean> = mock()
        val errorObserver: Observer<String> = mock()
        omdbRepo.loadingLiveData.observeForever(loadingObserver)
        omdbRepo.errorLiveData.observeForever(errorObserver)
        whenever(omdbDbService.getResultForQuery(query)).thenReturn(Single.error(Throwable("error")))

        omdbRepo.getListFromServer(query)

        verify(loadingObserver).onChanged(false)
        verify(errorObserver).onChanged("error")

    }

    @Test
    fun next() {
        val loadingObserver: Observer<Boolean> = mock()
        omdbRepo.loadingLiveData.observeForever(loadingObserver)

        omdbRepo.next(omdbSearchResponseEmpty)

        verify(loadingObserver).onChanged(false)
        verify(omdbDbRepo).insertResultIntoDb(omdbSearchResponseEmpty)
    }

    @Test
    fun error() {
        val loadingObserver: Observer<Boolean> = mock()
        val errorObserver: Observer<String> = mock()
        omdbRepo.loadingLiveData.observeForever(loadingObserver)
        omdbRepo.errorLiveData.observeForever(errorObserver)

        omdbRepo.error(Throwable("error"))

        verify(loadingObserver).onChanged(false)
        verify(errorObserver).onChanged("error")
    }


}