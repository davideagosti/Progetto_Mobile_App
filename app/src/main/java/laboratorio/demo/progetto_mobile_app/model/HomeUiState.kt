package laboratorio.demo.progetto_mobile_app.model

import android.location.Location
import laboratorio.demo.progetto_mobile_app.utils.PlaceInfo

data class HomeUiState(
    val locationPermissionGranted: Boolean = false,
    val locationEnabled: Boolean = false,
    val currentLocation: Location? = null,
    val selectedPlace: PlaceInfo? = null,
    val showPlaceInfoCard: Boolean = false
)