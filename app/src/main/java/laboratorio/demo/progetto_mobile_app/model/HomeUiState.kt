package laboratorio.demo.progetto_mobile_app.model

import android.location.Location
import laboratorio.demo.progetto_mobile_app.utils.PlaceInfo

/**
 * Rappresenta lo stato dell'interfaccia della Home.
 *
 * Contiene le informazioni necessarie alla UI per visualizzare
 * posizione, luogo selezionato, mappa e relativi stati.
 */
data class HomeUiState(
    val locationPermissionGranted: Boolean = false,
    val locationEnabled: Boolean = false,
    val currentLocation: Location? = null,
    val selectedPlace: PlaceInfo? = null,
    val showPlaceInfoCard: Boolean = false
)