package laboratorio.demo.progetto_mobile_app.screens

import android.location.Location
import kotlinx.coroutines.launch

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.libraries.places.api.model.AutocompletePrediction

import laboratorio.demo.progetto_mobile_app.components.AccountMenu
import laboratorio.demo.progetto_mobile_app.components.PlaceSuggestions
import laboratorio.demo.progetto_mobile_app.components.LocationPermissionHandler
import laboratorio.demo.progetto_mobile_app.components.MapSection
import laboratorio.demo.progetto_mobile_app.components.SearchBar
import laboratorio.demo.progetto_mobile_app.components.PlaceInfoCard
import laboratorio.demo.progetto_mobile_app.components.cities
import laboratorio.demo.progetto_mobile_app.utils.FavoritesManager
import laboratorio.demo.progetto_mobile_app.utils.RouteManager

import laboratorio.demo.progetto_mobile_app.utils.GeocoderManager
import laboratorio.demo.progetto_mobile_app.utils.LocationManager
import laboratorio.demo.progetto_mobile_app.utils.PlacesManager
import laboratorio.demo.progetto_mobile_app.utils.PlaceInfo

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,

    favoritePlaceId: String? = null,

    favoritePlaceName: String? = null,

    favoritePlaceAddress: String? = null,

    favoriteLatitude: Double? = null,

    favoriteLongitude: Double? = null
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

    val placesManager = remember {
        PlacesManager(context)
    }

    val favoritesManager = remember {
        FavoritesManager()
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val snackbarScope = rememberCoroutineScope()

    var placeSuggestions by remember {
        mutableStateOf(emptyList<AutocompletePrediction>())
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

    var selectedPlace by remember {
        mutableStateOf<PlaceInfo?>(null)
    }

    var showPlaceInfoCard by remember {
        mutableStateOf(false)
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

    val routeManager = remember {
        RouteManager(context)
    }

    var routePoints by remember {
        mutableStateOf<List<LatLng>>(emptyList())
    }

    val useTestLocation = false

    //    var useTestLocation by remember {
    //        mutableStateOf(true)
    //    }

    // Posizione iniziale della mappa: Bologna
    //val bologna = LatLng(44.4949, 11.3426)

    // Posizione predefinito Test
    val defaultLocation = LatLng(
        44.16452,   // latitudine indicativa di Cesena
        12.21926    // longitudine indicativa di Cesena
    )


    val initialLocation =
        if (
            favoriteLatitude != null &&
            favoriteLongitude != null
        ) {
            LatLng(
                favoriteLatitude,
                favoriteLongitude
            )
        } else {
            //bologna
            defaultLocation
        }


    // ==========================================
    // STATO DELLA TELECAMERA MAPPA
    // ==========================================

    // Mantiene la posizione e lo zoom attuali
    // della Google Map.
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            initialLocation,

            if (
                favoriteLatitude != null &&
                favoriteLongitude != null
            ) {
                16f
            } else {
                13f
            }
        )
    }

    // ==========================================
    // SPOSTAMENTO DELLA MAPPA
    // ==========================================

    // Quando viene selezionata una città,
    // spostiamo la telecamera sulla sua posizione.
    LaunchedEffect(searchedLocation) {

        val location = searchedLocation ?: return@LaunchedEffect

        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(
                location,
                16f
            )
        )
    }

    // ==========================================
    // APERTURA DI UN PREFERITO
    // ==========================================

    LaunchedEffect(
        favoritePlaceId,
        favoritePlaceName,
        favoritePlaceAddress,
        favoriteLatitude,
        favoriteLongitude
    ) {

        if (
            favoritePlaceId != null &&
            favoriteLatitude != null &&
            favoriteLongitude != null
        ) {

            val location = LatLng(
                favoriteLatitude,
                favoriteLongitude
            )

            // Imposta la posizione sulla mappa
            searchedLocation = location

            // Crea le informazioni del luogo preferito
            selectedPlace = PlaceInfo(
                placeId = favoritePlaceId,

                name = favoritePlaceName
                    ?: "Luogo preferito",

                address = favoritePlaceAddress
                    ?: "Indirizzo non disponibile",

                location = location
            )

            // Search Bar
            searchText = favoritePlaceName ?: ""

            // Mostra il nome nella SearchBar

            snackbarScope.launch {
                snackbarHostState.showSnackbar(
                    "📍 ${favoritePlaceName ?: "Luogo preferito"}"
                )
            }

        }
    }

    // ==========================================
// CALCOLO DEL PERCORSO
// ==========================================

    LaunchedEffect(
        currentLocation,
        selectedPlace,
        useTestLocation
    ) {

        // Destinazione
        val destination = selectedPlace?.location
            ?: return@LaunchedEffect

        // Posizione di partenza
        val origin : LatLng

        if (useTestLocation) {

            // ======================================
            // MODALITÀ TEST
            // ======================================

            origin = defaultLocation

            println(
                "📍 Modalità TEST"
            )

            println(
                "Partenza: Via dell'Arrigoni 260"
            )

        } else {

            // ======================================
            // MODALITÀ GPS REALE
            // ======================================

            val location = currentLocation
                ?: return@LaunchedEffect

            origin = LatLng(
                location.latitude,
                location.longitude
            )

            println(
                "📍 Modalità GPS REALE"
            )

            println(
                "Posizione: " +
                        "${location.latitude}, " +
                        "${location.longitude}"
            )
        }


        // ------------------------------------------
        // CALCOLO ROUTE
        // ------------------------------------------

        val result = routeManager.calculateRoute(
            origin = origin,
            destination = destination
        )


        // ------------------------------------------
        // RISULTATO
        // ------------------------------------------

        if (result != null) {

            routePoints = result.points

            println(
                "✅ Percorso trovato"
            )

            println(
                "📏 Distanza: " +
                        "${result.distanceMeters} metri"
            )

            println(
                "⏱ Durata: " +
                        "${result.durationSeconds} secondi"
            )

        } else {

            routePoints = emptyList()

            println(
                "❌ Impossibile calcolare il percorso"
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

                            if (text.isNotBlank()) {

                                // Mostra i suggerimenti quando l'utente
                                // inizia a digitare.
                                showSuggestions = true

                                placesManager.getSuggestions(text) { suggestions ->

                                    placeSuggestions = suggestions
                                }

                            } else {

                                showSuggestions = false
                                placeSuggestions = emptyList()
                            }

                            // Mostra i suggerimenti quando l'utente
                            // inizia a digitare.
                            // showSuggestions = text.isNotBlank()
                        },

                        onSearch = {

                            val query = searchText.trim()

                            if (query.isNotBlank()) {

                                // Prima controlla le città predefinite.
                                val city = cities.firstOrNull{ city ->

                                    city.name.equals(
                                        query,
                                        ignoreCase = true
                                    )
                                }

                                if (city != null) {

                                    // Città trovata nella lista locale.
                                    searchText = city.name

                                    searchedLocation = LatLng(
                                        city.latitude,
                                        city.longitude
                                    )

                                    // La città non ha una PlaceInfo dettagliata.
                                    selectedPlace = null

                                    // Nasconde i suggerimenti.
                                    showSuggestions = false

                                    // Rimuove il focus.
                                    focusManager.clearFocus()

                                    // Nasconde la tastiera.
                                    keyboardController?.hide()

                                } else {
                                    // Se non è una delle città predefinite,
                                    // utilizza il Geocoder.
                                    geocoderManager.search(query) { location ->

                                        searchedLocation = location

                                        // Nasconde i suggerimenti
                                        showSuggestions = false

                                        // Rimuove il focus.
                                        focusManager.clearFocus()

                                        // Nasconde la tastiera.
                                        keyboardController?.hide()
                                    }
                                }
                            }
                        }

                    )

                    // =====================================
                    // SUGGERIMENTI
                    // =====================================

                    if (showSuggestions && placeSuggestions.isNotEmpty()) {

                        PlaceSuggestions(
                            suggestions = placeSuggestions,

                            onPlaceSelected = { prediction ->

                                // Mostra nella SearchBar il luogo selezionato.
                                searchText = prediction.getPrimaryText(null).toString()

                                // Recupera le coordinate del luogo da Google Places.
                                placesManager.getPlaceLocation(prediction) { placeInfo ->

                                    if (placeInfo != null) {

                                        // Nome del luogo nella SearchBar.
                                        searchText = placeInfo.name

                                        // Salva tutte le informazioni del luogo.
                                        selectedPlace = placeInfo

                                        // Aggiorna la posizione utilizzata dalla mappa.
                                        searchedLocation = placeInfo.location

                                        // Mostra InfoCard
                                        showPlaceInfoCard = true

                                        // Nasconde i suggerimenti.
                                        showSuggestions = false

                                        // Svuota la lista dei suggerimenti.
                                        placeSuggestions = emptyList()

                                        // Rimuove il focus dalla SearchBar.
                                        focusManager.clearFocus()

                                        // Nasconde la tastiera.
                                        keyboardController?.hide()
                                    }
                                }
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

            if (showPlaceInfoCard) {
                // Info Card del luogo selezionato
                selectedPlace?.let { place ->

                    PlaceInfoCard(
                        place = place,
                        favoritesManager = favoritesManager,
                        onMessage = { message ->

                            snackbarScope.launch {
                                snackbarHostState.showSnackbar(message)
                            }
                        },

                        onClose = {

                            //selectedPlace = null

                            showPlaceInfoCard = false
                        }
                    )
                }
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

                selectedPlace = selectedPlace,

                searchText = searchText,

                locationPermissionGranted = locationPermissionGranted,

                currentLocation = currentLocation,

                routePoints = routePoints,

                useTestLocation = useTestLocation,

                defaultLocation = defaultLocation,

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
                },

                // Funziona solo quando il
                // marker corrisponde a un luogo selezionato da Places
                onMarkerClick = { place ->

                    selectedPlace = place
                    showPlaceInfoCard = true


                    snackbarScope.launch {
                        snackbarHostState.showSnackbar(

                            "Marker cliccato: ${selectedPlace!!.name}"
                        )
                    }
                }
            )
        }

        // Messaggio
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
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