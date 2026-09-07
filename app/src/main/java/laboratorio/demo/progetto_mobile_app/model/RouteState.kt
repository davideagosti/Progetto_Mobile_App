package laboratorio.demo.progetto_mobile_app.model

import com.google.android.gms.maps.model.LatLng

/**
 * Rappresenta lo stato del calcolo e della visualizzazione
 * del percorso richiesto dall'utente.
 */
data class RouteState(
    val origin: LatLng? = null,
    val destination: LatLng? = null,
    val originText: String = "",
    val destinationText: String = "",
    val searchMode: RouteSearchMode? = null,
    val isModeActive: Boolean = false,
    val isConfirmed: Boolean = false,
    val points: List<LatLng> = emptyList()
)

enum class RouteSearchMode {
    ORIGIN,
    DESTINATION
}