package laboratorio.demo.progetto_mobile_app.screens

import androidx.compose.foundation.layout.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.MaterialTheme

import laboratorio.demo.progetto_mobile_app.components.AccountMenu
import laboratorio.demo.progetto_mobile_app.components.SearchBar
import laboratorio.demo.progetto_mobile_app.components.MapSection
import laboratorio.demo.progetto_mobile_app.components.MapControls
import laboratorio.demo.progetto_mobile_app.utils.LocationManager
import laboratorio.demo.progetto_mobile_app.utils.GeocoderManager
import laboratorio.demo.progetto_mobile_app.components.LocationPermissionHandler

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState

import com.google.android.gms.maps.CameraUpdateFactory

import android.location.Location
import laboratorio.demo.progetto_mobile_app.components.CitySuggestions
import laboratorio.demo.progetto_mobile_app.components.cities

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
    ) {

    // ==========================================
    // CONTEXT
    // ==========================================

    // Context utilizzato dai componenti Android.
    val context = LocalContext.current

    // ==========================================
    // GESTIONE POSIZIONE
    // ==========================================

    // Gestisce permessi e recupero della posizione.
    val locationManager = remember {
        LocationManager(context)
    }

    // Indica se l'utente ha concesso almeno
    // uno dei permessi di localizzazione.
    var locationPermissionGranted by remember {
        mutableStateOf(
            locationManager.hasLocationPermission()
        )
    }

    // Gestisce la richiesta dei permessi Android.
    LocationPermissionHandler(
        locationPermissionGranted = locationPermissionGranted,
        onPermissionResult = { granted ->
            locationPermissionGranted = granted
        }
    )

    // ==========================================
    // GESTIONE RICERCA
    // ==========================================

    // Gestisce la ricerca di luoghi tramite Geocoder.
    val geocoderManager = remember {
        GeocoderManager(context)
    }

    // Testo inserito nella barra di ricerca
    var searchText by remember {
        mutableStateOf("")
    }

    // Indica se la tendina dei suggerimenti deve essere visibile.
    var showSuggestions by remember {
        mutableStateOf(false)
    }

    // Gestisce il focus della SearchBar.
    val focusManager = LocalFocusManager.current

    // Controller utilizzato per nascondere
    // la tastiera quando termina la ricerca.
    val keyboardController = LocalSoftwareKeyboardController.current

    // Posizione trovata dalla ricerca
    // Null significa che non è stata ancora trovata
    // nessuna posizione.
    var searchedLocation by remember {
        mutableStateOf<LatLng?>(null)
    }

    // ==========================================
    // POSIZIONE ATTUALE
    // ==========================================

    // Ultima posizione conosciuta dell'utente.
    var currentLocation by remember {
        mutableStateOf<Location?>(null)
    }

    // ==========================================
    // RECUPERO POSIZIONE INIZIALE
    // ==========================================

    // Quando l'utente concede il permesso,
    // l'app prova a recuperare l'ultima posizione conosciuta.
    LaunchedEffect(locationPermissionGranted) {

        if (locationPermissionGranted) {

            locationManager.getLastLocation { location ->

                currentLocation = location
            }
        }
    }

    // ==========================================
    // POSIZIONE INIZIALE DELLA MAPPA
    // ==========================================

    // Posizione iniziale della mappa: Bologna
    val bologna = LatLng(44.4949, 11.3426)


    // ==========================================
    // STATO DELLA TELECAMERA
    // ==========================================

    // Mantiene la posizione e lo zoom attuali
    // della Google Map.
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            bologna,
            13f
        )
    }

    // ==========================================
    // SPOSTAMENTO DELLA MAPPA
    // ==========================================

    // Quando viene selezionata una città,
    // spostiamo la telecamera sulla sua posizione.
    LaunchedEffect(searchedLocation) {

        searchedLocation?.let { location ->

            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(
                    location,
                    13f
                )
            )
        }
    }

    // ==========================================
    // BOX PRINCIPALE (INTERFACCIA)
    // ==========================================

    Box(
        modifier = Modifier
            .fillMaxSize()
        //.wrapContentSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            // =========================
            // BARRA SUPERIORE HOME
            // =========================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    // Barra ricerca
                    SearchBar(
                        modifier = Modifier.fillMaxWidth(),
                        searchText = searchText,
                        onSearchTextChange = { text ->

                            searchText = text

                            // Mostra i suggerimenti quando l'utente
                            // inizia a digitare.
                            showSuggestions = text.isNotBlank()
                        },

                        onSearch = {

                            val query = searchText.trim()

                            if (query.isNotBlank()) {

                                val city = cities.firstOrNull{
                                    it.name.equals(
                                        query,
                                        ignoreCase = true
                                    )
                                }

                                if (city != null) {

                                    searchText = city.name

                                    searchedLocation = LatLng(
                                        city.latitude,
                                        city.longitude
                                    )

                                    // Nasconde i suggerimenti.
                                    showSuggestions = false

                                    // Rimuove il cursore dalla SearchBar.
                                    focusManager.clearFocus()

                                    // Nasconde la tastiera.
                                    keyboardController?.hide()

                                } else {
                                    // Se non è una delle città
                                    // predefinite, utilizza
                                    // il Geocoder.
                                    geocoderManager.search(query) { location ->

                                        searchedLocation = location

                                        // Nasconde i suggerimenti
                                        // dopo la ricerca.
                                        showSuggestions = false

                                        // Rimuove il focus.
                                        focusManager.clearFocus()

                                        // Nasconde la tastiera.
                                        keyboardController?.hide()
                                    }
                                }
                            }
                            /*
                            val city = cities.firstOrNull{
                                it.name.equals(
                                    searchText.trim(),
                                    ignoreCase = true
                                )
                            }

                            if (city != null){
                                searchedLocation = LatLng(
                                    city.latitude,
                                    city.longitude
                                )
                            }*/

                            /*
                            // Evita di effettuare una ricerca vuota.
                            if (searchText.isNotBlank()) {
                                return@SearchBar
                            }
                            //val query = searchText.trim()

                            // Deleghiamo la ricerca al GeocoderManager.
                            geocoderManager.search(searchText) { location ->
                                //geocoderManager.search(query) { location ->

                                // Aggiorniamo la posizione trovata.
                                // Se location è null, non è stato trovato alcun risultato.
                                searchedLocation = location
                            }*/
                            //}
                        }

                    )

                    // =====================================
                    // SUGGERIMENTI
                    // =====================================

                    if (showSuggestions) {
                        CitySuggestions(
                            query = searchText,
                            onCitySelected = { city ->

                                // Inserisce il nome corretto
                                // nella SearchBar.
                                searchText = city.name

                                // Imposta la posizione selezionata.
                                // Salva la posizione selezionata.
                                searchedLocation = LatLng(
                                    city.latitude,
                                    city.longitude
                                )

                                // Nasconde la tendina.
                                showSuggestions = false

                                // Toglie il focus dalla SearchBar.
                                focusManager.clearFocus()

                                // Nasconde la tastiera.
                                keyboardController?.hide()

                            }
                        )
                    }
                }

                // Spazio tra barra ricerca e icona account
                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                // Account Menu
                AccountMenu(navController)
            }

            // =========================
            // GOOGLE MAPS
            // =========================

            MapSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),

                cameraPositionState = cameraPositionState,

                searchedLocation = searchedLocation,

                searchText = searchText,

                locationPermissionGranted = locationPermissionGranted,

                currentLocation = currentLocation,

                onZoomIn = {
                    cameraPositionState.move(
                        CameraUpdateFactory.zoomIn()
                    )
                },

                onZoomOut = {
                    cameraPositionState.move(
                        CameraUpdateFactory.zoomOut()
                    )
                },

                onMyLocationClick = {

                    // Controlla che il permesso sia disponibile.
                    if (locationPermissionGranted) {

                        locationManager.getLastLocation {
                            location ->

                                if (location != null) {

                                    // Salva la posizione attuale.
                                    currentLocation = location

                                    // Sposta la telecamera sulla posizione dell'utente.
                                    cameraPositionState.move(
                                        CameraUpdateFactory.newLatLngZoom(
                                            LatLng(
                                                location.latitude,
                                                location.longitude
                                            ),
                                            16f
                                        )
                                    )
                                }
                        }
                    }
                }
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