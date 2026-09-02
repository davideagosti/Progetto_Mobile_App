package laboratorio.demo.progetto_mobile_app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

import android.location.Location

import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompletePrediction

import laboratorio.demo.progetto_mobile_app.utils.PlaceInfo

import laboratorio.demo.progetto_mobile_app.model.HomeUiState
import laboratorio.demo.progetto_mobile_app.model.SearchState

class HomeViewModel : ViewModel() {

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    var searchState by mutableStateOf(SearchState())
        private set

    fun updateSearchState(newState: SearchState) {
        searchState = newState
    }

    // =============================
    // HomeUiState
    // =============================

    fun setLocationPermissionGranted(granted: Boolean) {
        homeUiState = homeUiState.copy(
            locationPermissionGranted = granted
        )
    }

    fun setCurrentLocation(location: Location?) {
        homeUiState = homeUiState.copy(
            currentLocation = location
        )
    }

    fun selectPlace(place: PlaceInfo) {
        homeUiState = homeUiState.copy(
            selectedPlace = place,
            showPlaceInfoCard = true
        )
    }

    fun clearSelectedPlace() {
        homeUiState = homeUiState.copy(
            selectedPlace = null,
            showPlaceInfoCard = false
        )
    }

    fun closePlaceInfoCard() {
        homeUiState = homeUiState.copy(
            showPlaceInfoCard = false
        )
    }

    // =============================
    // SearchState
    // =============================

    fun updateSearchText(text: String) {
        searchState = searchState.copy(
            text = text,
            showSuggestions = text.isNotBlank()
        )
    }

    fun setSuggestions(suggestions: List<AutocompletePrediction>) {
        searchState = searchState.copy(
            suggestions = suggestions
        )
    }

    fun showSuggestions(suggestions: List<AutocompletePrediction>) {
        searchState = searchState.copy(
            suggestions = suggestions,
            showSuggestions = true
        )
    }

    fun clearSuggestions() {
        searchState = searchState.copy(
            showSuggestions = false,
            suggestions = emptyList()
        )
    }

    fun setSearchLocation(location: LatLng?) {
        searchState = searchState.copy(
            location = location
        )
    }

    fun selectSearchPlace(
        place: PlaceInfo
    ) {
        searchState = searchState.copy(
            text = place.name,
            location = place.location
        )
    }

    // =============================
    // RouteState
    // =============================
}