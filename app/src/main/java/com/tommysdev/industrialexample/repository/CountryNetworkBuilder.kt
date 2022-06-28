package com.tommysdev.industrialexample.repository

import com.tommysdev.industrialexample.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountryNetworkBuilder {

    fun retrofitBuilder()  : CountryService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryService::class.java)
    }
}