package dev.elizacamber.glitzglamor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.elizacamber.glitzglamor.data.CityWithVisitDetails
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface CityDetailsUiState {
    val isLoading: Boolean
    val city: CityWithVisitDetails?

    data class CityError(
        override val isLoading: Boolean,
        override val city: CityWithVisitDetails?
    ) : CityDetailsUiState

    data class CitySuccess(
        override val isLoading: Boolean,
        override var city: CityWithVisitDetails
    ) : CityDetailsUiState
}

data class CityDetailsViewModelState(
    val city: CityWithVisitDetails? = null,
    val isLoading: Boolean = false,
) {
    fun toUiState(): CityDetailsUiState =
        when (city) {
            null -> CityDetailsUiState.CityError(
                isLoading = isLoading,
                city = null,
            )
            else -> CityDetailsUiState.CitySuccess(
                isLoading = false,
                city = city
            )
        }
}

class CityDetailsViewModel : ViewModel() {
    private val dao = Holder.database.citiesDao
    private val viewModelState = MutableStateFlow(CityDetailsViewModelState())

    init {
        viewModelState.update {
            it.copy(city = null, isLoading = true)
        }
    }

    // UI state exposed to the UI
    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun cityFlow(id: Long) = viewModelScope.launch {
        dao.getCityWithVisitDetails(id).collect { city ->
            viewModelState.update { it.copy(city = city, isLoading = false) }
        }
    }
}