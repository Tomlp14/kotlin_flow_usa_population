package com.tommysdev.industrialexample.repository

import com.tommysdev.industrialexample.util.END_POINT
import retrofit2.Response
import retrofit2.http.GET

interface CountryService {

    @GET(END_POINT)
    suspend fun getPoulationByYear(): Response<CountryResponse>
}