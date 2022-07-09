package dev.elizacamber.glitzglamor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.elizacamber.glitzglamor.database.CitiesDao

class CityDetailsViewModel(private val citiesDao: CitiesDao) : ViewModel() {

//    fun cities(): Flow<List<City>> = citiesDao.getAllCities()

//    fun insertCity(city: City) {
//        viewModelScope.launch {
//            citiesDao.insert(city)
//        }
//    }
}

class CityDetailsViewModelFactory(private val citiesDao: CitiesDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        super.create(modelClass)
        if (modelClass.isAssignableFrom(CityDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CityDetailsViewModel(citiesDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}