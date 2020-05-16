package com.ayush.noonacademy.repo.db

import androidx.lifecycle.MutableLiveData
import com.ayush.noonacademy.repo.OmdbRepoImpl
import com.ayush.noonacademy.repo.domain.OmdbItem
import com.ayush.noonacademy.repo.domain.OmdbSearchResponse
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*


//TODO facing error with Dao, need to understand how to mock room db as it holds context
class OmdbDbRepoImplTest {
//    private val db  = mock<OmdbDb>()
//    private val dao: OmdbDao = mock()
//    private lateinit var omdbdbRepo : OmdbDbRepoImpl
//    private val omdbItem = OmdbItem(
//        title = "title",
//        imdbID = "iid",
//        year = "year",
//        type = "type",
//        poster = "poster"
//    )
//
//    private val list = listOf(omdbItem)
//    private val omdbSearchResponseEmpty = OmdbSearchResponse(listOf(), 200, "response")
//    private val omdbSearchResponseNull = OmdbSearchResponse(null, 200, "response")
//    private val omdbSearchResponse = OmdbSearchResponse(list, 200, "response")
//
//    @Before
//    fun setUp() {
//
//        whenever(db.posts()).thenReturn(dao)
//        omdbdbRepo = OmdbDbRepoImpl(db)
//    }
//
//    @Test
//    fun insertResultIntoDb_ListIsNull() {
//        omdbdbRepo.insertResultIntoDb(omdbSearchResponseNull)
//        verify(dao, never()).insert(any())
//    }
//
//    @Test
//    fun insertResultIntoDb_ListIsEmpty() {
//        omdbdbRepo.insertResultIntoDb(omdbSearchResponseEmpty)
//        verify(dao, never()).insert(any())
//    }
//
//    @Test
//    fun insertResultIntoDb_ListIsNotEmpty() {
//        omdbdbRepo.insertResultIntoDb(omdbSearchResponse)
//        verify(dao).insert(list)
//    }
//
//    @Test
//    fun searchQuery() {
//        val query = "query"
//        val postList = MutableLiveData<List<OmdbItem>>().apply { value = list }
//        whenever(dao.getPosts(query)).thenReturn(postList)
//
//        verify(dao).getPosts(query)
//        assertEquals(omdbdbRepo.searchQuery(query).value, list)
//    }
}