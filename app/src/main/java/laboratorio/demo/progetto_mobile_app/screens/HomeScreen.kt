package laboratorio.demo.progetto_mobile_app.screens

import androidx.compose.foundation.layout.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import laboratorio.demo.progetto_mobile_app.components.AccountMenu
import laboratorio.demo.progetto_mobile_app.components.SearchBar
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

import android.os.Looper
import android.os.Handler
import android.location.Geocoder
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.modifier.modifierLocalConsumer
import com.google.android.gms.maps.CameraUpdateFactory

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
    ) {

    // ==========================================
    // TESTO DELLA RICERCA
    // ==========================================

    // Testo inserito nella barra di ricerca
    var searchText by remember {
        mutableStateOf("")
    }

    // ==========================================
    // POSIZIONE TROVATA
    // ==========================================

    // Posizione trovata dalla ricerca
    var searchedLocation by remember {
        mutableStateOf<LatLng?>(null)
    }

    // ==========================================
    // CONTEXT
    // ==========================================

    // Context necessario per utilizzare Geocoder
    val context = LocalContext.current

    var menuOpen by remember {
        mutableStateOf(false)
    }

    // Controlla la visualizzazione della finestra di conferma
    var showExitDialog by remember {
        mutableStateOf(false)
    }

    // Recupera l'Activity corrente
    val activity = LocalContext.current as? Activity


    // ==========================================
    // POSIZIONE INIZIALE
    // Bologna
    // ==========================================

    // Posizione iniziale della mappa: Bologna
    val bologna = LatLng(44.4949, 11.3426)


    // ==========================================
    // STATO DELLA TELECAMERA
    // ==========================================

    // Stato della telecamera della mappa
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            bologna,
            13f
        )
    }

    // ==========================================
    // IMPOSTAZIONI CONTROLLI MAPPA
    // ==========================================

    val mapUiSettings = remember {

        MapUiSettings(
            zoomControlsEnabled = true,
            zoomGesturesEnabled = true,
            scrollGesturesEnabled = true,
            rotationGesturesEnabled = true,
            tiltGesturesEnabled = true,
            compassEnabled = true
        )
    }

    // ==========================================
    // BOX PRINCIPALE
    // ==========================================

    Box(
        modifier = Modifier
            .fillMaxSize()
            //.wrapContentSize()
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // =========================
            // Barra superiore Home
            // =========================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Barra ricerca
                SearchBar(
                    modifier = Modifier.weight(1f),
                    searchText = searchText,
                    onSearchTextChange = {
                        searchText = it
                    },
                    onSearch = {
                        // Controlliamo che l'utente
                        // abbia effettivamente scritto qualcosa
                        if (searchText.isNotBlank()) {

                            val query = searchText.trim()

                            Thread {
                                try {

                                    val geocoder = Geocoder(context)

                                    @Suppress("DEPRECATION")
                                    val addresses = geocoder.getFromLocationName(
                                        //searchText,
                                        query,
                                        1
                                    )

                                    if (!addresses.isNullOrEmpty()) {

                                        val address = addresses[0]

                                        println(
                                            "RISULTATO: ${address.latitude}, ${address.longitude}"
                                        )

                                        val location = LatLng(
                                            address.latitude,
                                            address.longitude
                                        )

                                        // Torniamo sul Main Thread
                                        // Torniamo al thread principale
                                        // perché stiamo modificando lo stato Compose
                                        Handler(
                                            Looper.getMainLooper()
                                        ).post {

                                            println("Nessun risultato trovato per: $query")
                                            searchedLocation = location
                                        }
                                    }

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }.start()
                        }
                    }
                )

                // Spazio tra barra ricerca e icona account
                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                AccountMenu(navController)
            }

            // =========================
            // GOOGLE MAPS
            // =========================

            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),

                cameraPositionState = cameraPositionState,

                uiSettings = mapUiSettings
            ) {

                // Marker della posizione cercata
                searchedLocation?.let { location ->

                    Marker(
                        state = MarkerState(
                            position = location
                        ),

                        title = searchText,

                        snippet = "Posizione cercata"
                    )
                }
            }
        }
    }

    // =========================
    // SPOSTAMENTO DELLA MAPPA/TELECAMERA
    // =========================

    LaunchedEffect(searchedLocation) {

        searchedLocation?.let { location ->

            cameraPositionState.animate(
//                update = com.google.android.gms.maps.CameraUpdateFactory
//                    .newLatLngZoom(
                update = CameraUpdateFactory.newLatLngZoom(
                        location,
                        15f
                ),

                durationMs = 1000
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {

    MaterialTheme {
        HomeScreen(
            navController = rememberNavController()
        )
    }
}