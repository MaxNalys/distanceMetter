package com.example.distancemetter.ui.main

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Button
import com.example.distancemetter.R
import com.example.distancemetter.data.model.CountryData
import com.example.distancemetter.data.remote.repository.CountryRepository

class MainActivity : AppCompatActivity() {
    private val countryViewModel: CountryViewModel by viewModels {
        CountryViewModelFactory(CountryRepository()) // Ініціалізуйте CountryRepository тут
    }
    private lateinit var countryList: List<CountryData>

    private lateinit var spinnerCountryPrimary: Spinner
    private lateinit var spinnerCountryFinal: Spinner
    private lateinit var spinnerCityPrimary: Spinner
    private lateinit var spinnerCityFinal: Spinner
    private lateinit var buttonSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ініціалізація Spinner'ів та кнопки
        spinnerCountryPrimary = findViewById(R.id.spinner_country_primary)
        spinnerCountryFinal = findViewById(R.id.spinner_country_final)
        spinnerCityPrimary = findViewById(R.id.spinner_city_primary)
        spinnerCityFinal = findViewById(R.id.spinner_city_final)
        buttonSubmit = findViewById(R.id.button_submit)

        setupSpinners()
        observeViewModel()
        countryViewModel.fetchCountries()
    }

    private fun setupSpinners() {
        spinnerCountryPrimary.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedCountryIso = countryList[position].iso2 // або .iso3
                countryViewModel.fetchCities(selectedCountryIso)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerCountryFinal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedCountry = countryList[position].name
                countryViewModel.fetchCities(selectedCountry)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        buttonSubmit.setOnClickListener {
            val primaryCity = spinnerCityPrimary.selectedItem?.toString() ?: "Not selected"
            val finalCity = spinnerCityFinal.selectedItem?.toString() ?: "Not selected"
            Toast.makeText(this, "Primary City: $primaryCity, Final City: $finalCity", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        countryViewModel.countries.observe(this) { countries ->
            countryList = countries
            val countryNames = countries.map { it.name }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countryNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCountryPrimary.adapter = adapter
            spinnerCountryFinal.adapter = adapter
        }

        countryViewModel.cities.observe(this) { cities ->
            if (cities.isNotEmpty()) {
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCityPrimary.adapter = adapter
                spinnerCityFinal.adapter = adapter
            } else {
                // Якщо немає міст, можна очистити спінери
                spinnerCityPrimary.adapter = null
                spinnerCityFinal.adapter = null
                Toast.makeText(this, "No cities found for selected country", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
