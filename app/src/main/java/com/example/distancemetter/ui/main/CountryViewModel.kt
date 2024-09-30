package com.example.distancemetter.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.distancemetter.data.model.CountryData
import com.example.distancemetter.data.remote.repository.CountryRepository
import kotlinx.coroutines.launch

class CountryViewModel(private val countryRepository: CountryRepository) : ViewModel() {
    private val _countries = MutableLiveData<List<CountryData>>()
    val countries: LiveData<List<CountryData>> get() = _countries

    private val _cities = MutableLiveData<List<String>>()
    val cities: LiveData<List<String>> get() = _cities

    fun fetchCountries() {
        viewModelScope.launch {
            val countryList = countryRepository.getCountries()
            _countries.postValue(countryList)
        }
    }

    fun fetchCities(country: String) {
        viewModelScope.launch {
            val cityList = countryRepository.getCities(country)
            _cities.postValue(cityList)
        }
    }
}
