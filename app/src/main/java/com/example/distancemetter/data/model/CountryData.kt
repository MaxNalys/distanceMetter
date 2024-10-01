package com.example.distancemetter.data.model

data class CountryResponse(
    val error: Boolean,
    val msg: String,
    val data: List<CountryData>
)

data class CountryData(
    val name: String,
    val currency: String,
    val iso2: String,
    val iso3: String
)
