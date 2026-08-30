package laboratorio.demo.progetto_mobile_app.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.CameraPositionState


import android.location.Location

import laboratorio.demo.progetto_mobile_app.utils.PlaceInfo

@Composable
fun MapSection(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    searchedLocation: LatLng?,
    selectedPlace: PlaceInfo?,
    searchText: String,
    locationPermissionGranted: Boolean,
    currentLocation: Location?,
    routePoints: List<LatLng>,

    useTestLocation: Boolean,
    defaultLocation: LatLng,

    onZoomIn: () -> Unit,
    onZoomOut: () -> Unit,
    onMyLocationClick: () -> Unit,
    onMarkerClick: (PlaceInfo) -> Unit
) {
    Box(
        modifier = modifier
    ) {

        // =========================
        // GOOGLE MAP
        // =========================

        GoogleMap(
            modifier = Modifier.fillMaxSize(),

            cameraPositionState = cameraPositionState,

            uiSettings = MapUiSettings(
                // Disabilitiamo i controlli predefiniti
                // perché utilizziamo MapControls
                zoomControlsEnabled = false,

                // Gestione normale della mappa
                zoomGesturesEnabled = true,
                scrollGesturesEnabled = true,
                rotationGesturesEnabled = true,
                tiltGesturesEnabled = true,

                compassEnabled = true,

                // IMPORTANTE:
                // disabilita il pulsante posizione
                // integrato di Google Maps
                myLocationButtonEnabled = false
            ),

            properties = MapProperties(
                isMyLocationEnabled = locationPermissionGranted
            )
        ) {

            // ==========================================
            // PERCORSO
            // ==========================================

            if (routePoints.isNotEmpty()) {

                Polyline(
                    points = routePoints,
                    width = 10f
                )
            }

            // ==========================================
            // MARKER POSIZIONE DI TEST
            // ==========================================

            if (useTestLocation) {

                Marker(
                    state = MarkerState(
                        position = defaultLocation
                    ),
                    title = "Posizione di test"
                )
            }

            // ==========================================
            // MARKER DELLA RICERCA
            // ==========================================

            // Marker della ricerca
            searchedLocation?.let { location ->

                Marker(
                    state = MarkerState(
                        position = location
                    ),

                    title = searchText,

                    onClick = {

                        selectedPlace?.let { place ->

                            onMarkerClick(place)
                        }

                        // true = il click è stato gestito
                        true
                    }
                )
            }
        }

        // ==========================================
        // CONTROLLI PERSONALIZZATI
        // ==========================================

        MapControls(
            onZoomIn = onZoomIn,

            onZoomOut = onZoomOut,

            onMyLocation = onMyLocationClick,

            modifier = Modifier
                .align(Alignment.BottomEnd)
        )


        // =========================
        // PULSANTE POSIZIONE
        // =========================
        /*
        FloatingActionButton(
            onClick = onMyLocationClick,

            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 16.dp,
                    bottom = 80.dp
                )
        ) {

            Icon(
                imageVector = Icons.Default.MyLocation,
                contentDescription = "La mia posizione"
            )
        }*/
    }
}
