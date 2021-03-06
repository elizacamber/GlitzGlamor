package dev.elizacamber.glitzglamor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.elizacamber.glitzglamor.data.City
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface CitiesUiState {
    val isLoading: Boolean
    val cities: List<City>

    data class CitiesEmpty(
        override val isLoading: Boolean,
        override val cities: List<City> = listOf()
    ) : CitiesUiState

    data class CitiesSuccess(
        override val isLoading: Boolean,
        override val cities: List<City>
    ) : CitiesUiState
}

data class CitiesViewModelState(
    val cities: List<City>? = null,
    val isLoading: Boolean = false,
) {
    fun toUiState(): CitiesUiState =
        when {
            cities.isNullOrEmpty() -> CitiesUiState.CitiesEmpty(
                isLoading = isLoading
            )
            else -> CitiesUiState.CitiesSuccess(
                isLoading = false,
                cities = cities
            )
        }
}

class VisitedCitiesViewModel() : ViewModel() {

    private val dao = Holder.database.citiesDao
    private val viewModelState = MutableStateFlow(CitiesViewModelState())

    init {
        viewModelState.update {
            it.copy(cities = null, isLoading = true)
        }
        //prepopulate DB
//        viewModelScope.launch(Dispatchers.IO) {
//            createCountryList()
//            dummyCityList.forEach { dao.insert(it) }
//            dummyVisitsList.forEach { dao.insert(it) }
//        }
        cities()
    }

    // UI state exposed to the UI
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun cities() = viewModelScope.launch {
        delay(1000L) // mock loading delay
        dao.getAllCities().collect { cities ->
            viewModelState.update { it.copy(cities = cities, isLoading = false) }
        }
    }
}