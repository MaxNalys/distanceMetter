package com.example.distancemetter.data.remote.repository

import android.util.Log
import com.example.distancemetter.data.model.CountryData
import com.example.distancemetter.data.remote.api.CountryApi
import com.example.distancemetter.utils.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CountryRepository {
    private val countryApi: CountryApi = ApiService.create()

    suspend fun getCountries(): List<CountryData> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("CountryRepository", "Fetching countries from API...")
                val response = countryApi.getCountries()
                if (response.isSuccessful) {
                    response.body()?.data ?: emptyList()
                } else {
                    Log.e("CountryRepository", "Error fetching countries: ${response.errorBody()}")
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e("CountryRepository", "Error fetching countries: ${e.message}")
                emptyList()
            }
        }
    }

    suspend fun getCities(country: String): List<String> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("CountryRepository", "Fetching cities for country: $country")
                val response = countryApi.getCities(country)
                if (response.isSuccessful) {
                    Log.d("CountryRepository", "Cities fetched successfully: ${response.body()?.data}")
                    response.body()?.data ?: emptyList()
                } else {
                    Log.e("CountryRepository", "Error fetching cities: ${response.errorBody()}")
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e("CountryRepository", "Error fetching cities: ${e.message}")
                emptyList()
            }
        }
    }

}
