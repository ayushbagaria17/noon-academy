package com.ayush.noonacademy.ui

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ayush.TestSchedulerProvider
import com.ayush.noonacademy.repo.OmdbRepo
import com.ayush.noonacademy.repo.Response
import com.ayush.noonacademy.repo.domain.OmdbItem
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

class SearchViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val omdbRepo: OmdbRepo = mock()
    val schedulerProvider = TestSchedulerProvider()
    lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        viewModel = SearchViewModel(omdbRepo, schedulerProvider)
    }

    @Test
    fun searchTextChanged() {
        val testObserver = viewModel.searchSubject.test()
        viewModel.searchTextChanged("query", 0, 0, 0)

        testObserver.assertValue { it == "query" }
    }

    @Test
    fun observeQueryChanges() {
        assertEquals(View.GONE, viewModel.isEmptyViewVisible.get())
        assertEquals(View.GONE, viewModel.isListVisible.get())
        verify(omdbRepo, times(1)).runQuery("Friend")
    }

    @Test
    fun handleData_responeIsSuccess() {
        val omdbItem = OmdbItem(
            title = "title",
            imdbID = "iid",
            year = "year",
            type = "type",
            poster = "poster"
        )
        val list = listOf(omdbItem)
        val omdbSearchResponse = Response.Success(list)
        val listObserver: Observer<List<OmdbItem>> = mock()
        viewModel.listLiveData().observeForever(listObserver)

        viewModel.handleData(omdbSearchResponse)

        assertEquals(View.GONE, viewModel.isEmptyViewVisible.get())
        assertEquals(View.VISIBLE, viewModel.isListVisible.get())
        verify(listObserver).onChanged(list)
    }

    @Test
    fun handleData_responeIsError_butDbResultIsAlreadyVisible() {
        val omdbSearchResponse = Response.Error("Error")
        val listObserver: Observer<List<OmdbItem>> = mock()
        viewModel.isListVisible.set(View.VISIBLE)
        viewModel.isEmptyViewVisible.set(View.GONE)
        viewModel.listLiveData().observeForever(listObserver)

        viewModel.handleData(omdbSearchResponse)

        assertEquals(View.GONE, viewModel.isEmptyViewVisible.get())
        assertEquals(View.VISIBLE, viewModel.isListVisible.get())
        verify(listObserver, never()).onChanged(any())
    }

    @Test
    fun handleData_responeIsError_butLoadingViewVisible() {
        val omdbSearchResponse = Response.Error("Error")
        val listObserver: Observer<List<OmdbItem>> = mock()
        viewModel.isListVisible.set(View.GONE)
        viewModel.isEmptyViewVisible.set(View.VISIBLE)
        viewModel.listLiveData().observeForever(listObserver)

        viewModel.handleData(omdbSearchResponse)

        assertEquals(View.GONE, viewModel.isEmptyViewVisible.get())
        assertEquals(View.GONE, viewModel.isListVisible.get())
        verify(listObserver).onChanged(listOf())
    }

    @Test
    fun handleData_loadingIsTrue_dbResultIsAlreadyVisible() {
        val omdbSearchResponse = Response.Loading(true)
        val listObserver: Observer<List<OmdbItem>> = mock()
        viewModel.isListVisible.set(View.VISIBLE)
        viewModel.isEmptyViewVisible.set(View.GONE)
        viewModel.listLiveData().observeForever(listObserver)

        viewModel.handleData(omdbSearchResponse)

        assertEquals(View.GONE, viewModel.isEmptyViewVisible.get())
        assertEquals(View.VISIBLE, viewModel.isListVisible.get())
        verify(listObserver, never()).onChanged(any())
    }

    @Test
    fun handleData_loadingIsTrue_DbResultIsNotVisible() {
        val omdbSearchResponse = Response.Loading(true)
        val listObserver: Observer<List<OmdbItem>> = mock()
        viewModel.isListVisible.set(View.GONE)
        viewModel.isEmptyViewVisible.set(View.GONE)
        viewModel.listLiveData().observeForever(listObserver)

        viewModel.handleData(omdbSearchResponse)

        assertEquals(View.VISIBLE, viewModel.isEmptyViewVisible.get())
        assertEquals(View.GONE, viewModel.isListVisible.get())
        verify(listObserver).onChanged(listOf())
    }

    @Test
    fun handleData_loadingIsFalse() {
        val omdbSearchResponse = Response.Loading(false)
        val listObserver: Observer<List<OmdbItem>> = mock()
        viewModel.isEmptyViewVisible.set(View.GONE)
        viewModel.listLiveData().observeForever(listObserver)

        viewModel.handleData(omdbSearchResponse)

        assertEquals(View.GONE, viewModel.isEmptyViewVisible.get())
        verify(listObserver, never()).onChanged(any())
    }
}