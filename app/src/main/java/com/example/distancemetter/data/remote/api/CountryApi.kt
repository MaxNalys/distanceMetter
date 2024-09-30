package com.example.distancemetter.data.remote.api

import com.example.distancemetter.data.model.CountryData
import com.example.distancemetter.data.model.CityData
import com.example.distancemetter.data.model.CountryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryApi {
    @GET("countries/currency")
    suspend fun getCountries(): Response<CountryResponse>

    @GET("countries/cities/{country}")
    suspend fun getCities(@Path("country") country: String): Response<CityData>
}

