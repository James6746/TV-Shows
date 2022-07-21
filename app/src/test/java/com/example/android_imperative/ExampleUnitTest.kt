package com.example.android_imperative

import com.example.android_imperative.di.AppModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkStatusCode() = runTest {
        val response = AppModule().tvShowService().apiTVShowPopular(1)
        assertEquals(200, response.code())
    }

    @Test
    fun responseIsSuccessful() = runTest {
        val response = AppModule().tvShowService().apiTVShowPopular(1)
        assertTrue(response.isSuccessful)
    }

    @Test
    fun checkTVShowListNotNull() = runTest {
        val response = AppModule().tvShowService().apiTVShowPopular(1)
        assertNotNull(response.body())
        assertNotNull(response.body()!!.tv_shows)
    }

    @Test
    fun checkTVShowListSize() = runTest {
        val response = AppModule().tvShowService().apiTVShowPopular(1)
        val tvShowPopular = response.body()
        assertEquals(20, tvShowPopular!!.tv_shows.size)
    }

    @Test
    fun checkFirstTVShowStatus() = runTest {
        val response = AppModule().tvShowService().apiTVShowPopular(1)
        val tvShows = response.body()!!.tv_shows
        val tvShow = tvShows[0]
        assertEquals("Running", tvShow.status)
    }


    @Test
    fun `checking Arrow episode Count`() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(29560)
        val tvShowDetail = response.body()
        assertEquals(170, tvShowDetail!!.tvShow.episodes.size)
    }

    @Test
    fun `Check country`() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(29560)
        assertEquals("US", response.body()?.tvShow?.country)
    }

    @Test
    fun `is there sci-fi in genres`() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(29560)
        assertEquals("Action",response.body()?.tvShow?.genres!![1])
    }
}