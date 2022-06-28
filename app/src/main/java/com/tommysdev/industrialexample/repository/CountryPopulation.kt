package com.tommysdev.industrialexample.repository

import com.google.gson.annotations.SerializedName

data class CountryPopulation(
    @SerializedName("Nation")
    val nation: String?,

    @SerializedName("Year")
    val year: String?,

    @SerializedName("Population")
    var population: String?
)

data class CountryResponse(
    @SerializedName("data")
    val countriesList: List<CountryPopulation>
    )