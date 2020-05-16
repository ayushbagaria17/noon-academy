package com.ayush.noonacademy.repo.network

import com.ayush.noonacademy.repo.domain.OmdbSearchResponse
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test


internal class OmdbServiceImplTest {
    private val apiKey: String = "apiKey"
    private val omdbApi: OmdbApi = mock()
    lateinit var service: OmdbServiceImpl
    private val query = "query"
    private val omdbSearchResponse = OmdbSearchResponse(listOf(), 120, "Success")
    @Before
    fun setUp() {
        whenever(omdbApi.getListOfSeries(query, apiKey, 1)).thenReturn(Single.just(omdbSearchResponse))
        service = OmdbServiceImpl(apiKey, omdbApi)
    }

    @Test
    fun testGetOmbdList() {
        val testObserver = service.getResultForQuery(query).test()

        verify(omdbApi).getListOfSeries(query, apiKey, 1)
        testObserver.assertNoErrors().assertValue {
            it.response == omdbSearchResponse.response
                    && it.totalResults  == omdbSearchResponse.totalResults
        }.dispose()
    }
}