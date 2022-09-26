package com.fachrul.api_service

import com.fachrul.api_service.service.RetrofitClient
import com.fachrul.api_service.service.remote.DiscoverMovieService
import com.fachrul.common.entity.discover.DiscoverMovieResponse
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

}