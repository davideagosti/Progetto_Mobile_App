package laboratorio.demo.progetto_mobile_app.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.CameraPositionState

import android.location.Location

@Composable
fun MapSection(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    searchedLocation: LatLng?,
    searchText: String,
    locationPermissionGranted: Boolean,
    currentLocation: Location?,
    onZoomIn: () -> Unit,
    onZoomOut: () -> Unit,
    onMyLocationClick: () -> Unit
) {
    Box(
        modifier = modifier
//            .fillMaxWidth()
//            .weight(1f)
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
                // disabilitiamo il pulsante posizione
                // integrato di Google Maps
                myLocationButtonEnabled = false
            ),

            properties = MapProperties(
                isMyLocationEnabled = locationPermissionGranted
            )
        ) {

            // ==========================================
            // MARKER DELLA RICERCA
            // ==========================================

            // Marker della ricerca
            searchedLocation?.let { location ->

                Marker(
                    state = MarkerState(
                        position = location
                    ),

                    title = searchText
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
                .fillMaxSize()
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
