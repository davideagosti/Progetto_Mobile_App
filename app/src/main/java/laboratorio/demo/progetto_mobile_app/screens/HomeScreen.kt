package laboratorio.demo.progetto_mobile_app.screens

import android.location.Location
import kotlinx.coroutines.launch

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text

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

import laboratorio.demo.progetto_mobile_app.model.RouteState
import laboratorio.demo.progetto_mobile_app.model.RouteSearchMode
import laboratorio.demo.progetto_mobile_app.model.SearchState
import laboratorio.demo.progetto_mobile_app.model.HomeUiState

import laboratorio.demo.progetto_mobile_app.ui.theme.*

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
    var homeUiState by remember {
        mutableStateOf(
            HomeUiState(
                locationPermissionGranted = locationManager.hasLocationPermission()
            )
        )
    }

    // Gestisce la richiesta dei permessi Android.
    LocationPermissionHandler(
        locationPermissionGranted = homeUiState.locationPermissionGranted,
        onPermissionResult = { granted ->
            homeUiState = homeUiState.copy(
                locationPermissionGranted = granted
            )
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

    var searchState by remember {
        mutableStateOf(SearchState())
    }

    // Gestisce il focus della SearchBar.
    val focusManager = LocalFocusManager.current

    // Controller utilizzato per nascondere
    // la tastiera quando termina la ricerca.
    val keyboardController = LocalSoftwareKeyboardController.current

    var routeState by remember {
        mutableStateOf(
            RouteState(
                originText = "La mia posizione"
            )
        )
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
    LaunchedEffect(homeUiState.locationPermissionGranted) {

        if (homeUiState.locationPermissionGranted) {

            locationManager.getLastLocation { location ->

                homeUiState = homeUiState.copy(
                    currentLocation = location
                )
            }
        }
    }

    // ==========================================
    // IMPOSTA POSIZIONE GPS COME PARTENZA
    // ==========================================

    fun useCurrentLocationAsOrigin() {

        if (!homeUiState.locationPermissionGranted) {

            snackbarScope.launch {
                snackbarHostState.showSnackbar(
                    "Permesso di localizzazione non disponibile"
                )
            }

            return
        }

        locationManager.getLastLocation { location ->

            if (location != null) {

                homeUiState = homeUiState.copy(
                    currentLocation = location
                )

                routeState = routeState.copy(
                    origin = LatLng(
                        location.latitude,
                        location.longitude
                    )//,

                    //originText = "La mia posizione"
                )
            } else {

                snackbarScope.launch {
                    snackbarHostState.showSnackbar(
                        "Impossibile recuperare la posizione attuale"
                    )
                }
            }
        }
    }

    // ==========================================
    // POSIZIONE INIZIALE DELLA MAPPA
    // ==========================================

    val routeManager = remember {
        RouteManager(context)
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
    LaunchedEffect(searchState.location) {

        val location = searchState.location ?: return@LaunchedEffect

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
            searchState = searchState.copy(
                location = location
            )

            // Crea le informazioni del luogo preferito
            homeUiState = homeUiState.copy(
                selectedPlace = PlaceInfo(
                    placeId = favoritePlaceId,

                    name = favoritePlaceName
                        ?: "Luogo preferito",

                    address = favoritePlaceAddress
                        ?: "Indirizzo non disponibile",

                    location = location
                )
            )

            // Search Bar
            searchState = searchState.copy(
            text = favoritePlaceName ?: ""
            )

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
        routeState.origin,
        routeState.destination,
        routeState.isConfirmed
    ) {

        if (!routeState.isConfirmed) {
            return@LaunchedEffect
        }

        val origin = routeState.origin
            ?: return@LaunchedEffect

        val destination = routeState.destination
            ?: return@LaunchedEffect

        println("🗺 Calcolo percorso")

        println(
            "Partenza: " +
                    "${origin.latitude}, ${origin.longitude}"
        )

        println(
            "Destinazione: " +
                    "${destination.latitude}, ${destination.longitude}"
        )

        // ==========================================
        // CALCOLO ROUTE
        // ==========================================

        val result = routeManager.calculateRoute(
            origin = origin,
            destination = destination
        )

        // ==========================================
        // RISULTATO
        // ==========================================

        if (result != null) {

            routeState = routeState.copy(
                points = result.points
            )

            println("✅ Percorso trovato")

            println(
                "📏 Distanza: " +
                        "${result.distanceMeters} metri"
            )

            println(
                "⏱ Durata: " +
                        "${result.durationSeconds} secondi"
            )

        } else {

            routeState = routeState.copy(
                points = emptyList()
            )

            snackbarScope.launch {
                snackbarHostState.showSnackbar(
                    "❌ Impossibile calcolare il percorso"
                )
            }
        }
    }

    // ==========================================
    // BOX PRINCIPALE (INTERFACCIA)
    // ==========================================

    Box(
        modifier = Modifier
            .fillMaxSize()
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

                    if (!routeState.isModeActive) {
                        // Barra ricerca
                        SearchBar(
                            modifier = Modifier.fillMaxWidth(),
                            searchText = searchState.text,
                            onSearchTextChange = { text ->

                                searchState = searchState.copy(
                                    text = text,
                                    showSuggestions = text.isNotBlank()
                                )

                                if (text.isNotBlank()) {

                                    // Mostra i suggerimenti quando l'utente
                                    // inizia a digitare.
//                                    searchState = searchState.copy(
//                                        showSuggestions = true
//                                    )

                                    placesManager.getSuggestions(text) { suggestions ->

                                        searchState = searchState.copy(
                                            suggestions = suggestions
                                        )
                                    }

                                } else {

                                    searchState = searchState.copy(
                                        showSuggestions = false,
                                        suggestions = emptyList()
                                    )
                                }

                                // Mostra i suggerimenti quando l'utente
                                // inizia a digitare.
                                // showSuggestions = text.isNotBlank()
                            },

                            onSearch = {

                                val query = searchState.text.trim()

                                if (query.isNotBlank()) {

                                    // Prima controlla le città predefinite.
                                    val city = cities.firstOrNull { city ->

                                        city.name.equals(
                                            query,
                                            ignoreCase = true
                                        )
                                    }

                                    if (city != null) {

                                        // Città trovata nella lista locale.
                                        searchState = searchState.copy(
                                            text = city.name,
                                            location = LatLng(
                                                city.latitude,
                                                city.longitude
                                            ),

                                            // Nasconde i suggerimenti.
                                            showSuggestions = false,
                                            suggestions = emptyList()

                                        )

                                        // La città non ha una PlaceInfo dettagliata.
                                        homeUiState = homeUiState.copy(
                                            selectedPlace = null
                                        )

                                        // Rimuove il focus.
                                        focusManager.clearFocus()

                                        // Nasconde la tastiera.
                                        keyboardController?.hide()

                                    } else {
                                        // Se non è una delle città predefinite,
                                        // utilizza il Geocoder.
                                        geocoderManager.search(query) { location ->

                                            searchState = searchState.copy(
                                                location = location
                                            )

                                            // Nasconde i suggerimenti
                                            searchState = searchState.copy(
                                                showSuggestions = false
                                            )

                                            // Rimuove il focus.
                                            focusManager.clearFocus()

                                            // Nasconde la tastiera.
                                            keyboardController?.hide()
                                        }
                                    }
                                }
                            }

                        )
                    }

                    if (routeState.isModeActive) {

                        // ==========================================
                        // PARTENZA
                        // ==========================================

                        SearchBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),

                            searchText = routeState.originText,

                            placeholderText = "La mia posizione",

                            onSearchTextChange = { text ->

                                // Indica che stiamo cercando la partenza
                                routeState = routeState.copy(
                                    originText = text,
                                    searchMode = RouteSearchMode.ORIGIN
                                )

                                if (text.isNotBlank()) {

                                    placesManager.getSuggestions(text) { suggestions ->

                                        searchState = searchState.copy(
                                            suggestions = suggestions,
                                            showSuggestions = true
                                        )
                                    }

                                } else {

                                    searchState = searchState.copy(
                                        showSuggestions = false,
                                        suggestions = emptyList()
                                    )
                                }
                            },

                            onSearch = {

                                val query = routeState.originText.trim()

                                if (query.isNotBlank()) {

                                    geocoderManager.search(query) { location ->

                                        if (location != null) {

                                            routeState = routeState.copy(
                                                origin = location
                                            )
                                        }
                                    }
                                }
                            }
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        // ==========================================
                        // DESTINAZIONE
                        // ==========================================

                        SearchBar(
                            modifier = Modifier
                                .fillMaxWidth(),

                            searchText = routeState.destinationText,

                            placeholderText = "Inserisci destinazione",

                            onSearchTextChange = { text ->

                                // Indica che stiamo cercando la destinazione
                                routeState = routeState.copy(
                                    destinationText = text,
                                    searchMode = RouteSearchMode.DESTINATION
                                )

                                if (text.isNotBlank()) {

                                    placesManager.getSuggestions(text) { suggestions ->

                                        searchState = searchState.copy(
                                            suggestions = suggestions,
                                            showSuggestions = true
                                        )
                                    }

                                } else {

                                    searchState = searchState.copy(
                                        showSuggestions = false,
                                        suggestions = emptyList()
                                    )
                                }
                            },

                            onSearch = {

                                val query = routeState.destinationText.trim()

                                if (query.isNotBlank()) {

                                    geocoderManager.search(query) { location ->

                                        if (location != null) {

                                            routeState = routeState.copy(
                                                destination = location
                                            )
                                        }
                                    }
                                }
                            }
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        // ==========================================
                        // CONFERMA PERCORSO
                        // ==========================================

                        Button(
                            onClick = {

                                if (
                                    routeState.origin != null &&
                                    routeState.destination != null
                                ) {

                                    // Permette al LaunchedEffect
                                    // di calcolare il percorso
                                    routeState = routeState.copy(
                                        isConfirmed = true
                                    )

                                    snackbarScope.launch {
                                        snackbarHostState.showSnackbar(
                                            "🗺 Calcolo del percorso..."
                                        )
                                    }

                                } else {

                                    snackbarScope.launch {
                                        snackbarHostState.showSnackbar(
                                            "Inserisci partenza e destinazione"
                                        )
                                    }
                                }
                            },

                            modifier = Modifier.fillMaxWidth(),

                            colors = ButtonDefaults.buttonColors(
                                containerColor = lightBlue
                            )
                        ) {

                            Text("Conferma percorso")
                        }
                    }

                    // =====================================
                    // SUGGERIMENTI
                    // =====================================

                    if (searchState.showSuggestions &&
                        searchState.suggestions.isNotEmpty()) {

                        PlaceSuggestions(
                            suggestions = searchState.suggestions,

                            onPlaceSelected = { prediction ->

                                placesManager.getPlaceLocation(prediction) { placeInfo ->

                                    if (placeInfo != null) {

                                        // =====================================
                                        // RICERCA PARTENZA
                                        // =====================================

                                        when (routeState.searchMode) {

                                            RouteSearchMode.ORIGIN -> {

                                                routeState = routeState.copy(
                                                    originText = placeInfo.name,
                                                    origin = placeInfo.location
                                                )
                                            }

                                            // =====================================
                                            // RICERCA DESTINAZIONE
                                            // =====================================

                                            RouteSearchMode.DESTINATION -> {
                                                routeState = routeState.copy(
                                                    destinationText = placeInfo.name,
                                                    destination = placeInfo.location
                                                )
                                            }

                                            // =====================================
                                            // RICERCA NORMALE
                                            // =====================================

                                            null -> {

                                                searchState = searchState.copy(
                                                    text = placeInfo.name,
                                                    location = placeInfo.location
                                                )

                                                homeUiState = homeUiState.copy(
                                                    selectedPlace = placeInfo
                                                )

                                                homeUiState = homeUiState.copy(
                                                    showPlaceInfoCard = true
                                                )
                                            }
                                        }

                                        // Nasconde suggerimenti
                                        searchState = searchState.copy(
                                            showSuggestions = false
                                        )

                                        searchState = searchState.copy(
                                            suggestions = emptyList()
                                        )
                                        //placeSuggestions = emptyList()

                                        // Rimuove focus
                                        focusManager.clearFocus()

                                        // Nasconde tastiera
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

            // Pulsante di creazione percorso
            Button(
                onClick = {

                    val newMode = !routeState.isModeActive

                    if (newMode) {

                        // ==========================================
                        // APERTURA MODALITÀ PERCORSO
                        // ==========================================

                        routeState = routeState.copy(
                            isModeActive = true,
                            searchMode = RouteSearchMode.ORIGIN,
                            originText = "",
                            destinationText = "",
                            destination = null,
                            isConfirmed = false,
                            points = emptyList()
                        )

                        // Prova a usare la posizione GPS come partenza default
                        useCurrentLocationAsOrigin()

                    } else {

                        // ==========================================
                        // CHIUSURA MODALITÀ PERCORSO
                        // ==========================================

                        routeState = routeState.copy(
                            isModeActive = false,
                            origin = null,
                            destination = null,
                            originText = "",
                            destinationText = "",
                            searchMode = null,
                            isConfirmed = false,
                            points = emptyList()
                        )

                        // Quando chiudo la modalità percorso
                        // pulisco il percorso.
                    }
                },

                modifier = Modifier.fillMaxWidth(),

                colors = ButtonDefaults.buttonColors(
                    containerColor = lightBlue
                )
            ) {
                Text(
                    if (routeState.isModeActive)
                        "Chiudi percorso"
                    else
                        "Crea percorso"
                )
            }

            if (homeUiState.showPlaceInfoCard) {
                // Info Card del luogo selezionato
                homeUiState.selectedPlace?.let { place ->

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
                            homeUiState = homeUiState.copy(
                                showPlaceInfoCard = false
                            )
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

                searchedLocation = searchState.location,

                selectedPlace = homeUiState.selectedPlace,

                searchText = searchState.text,

                locationPermissionGranted = homeUiState.locationPermissionGranted,

                currentLocation = homeUiState.currentLocation,

                routePoints = routeState.points,

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
                    if (homeUiState.locationPermissionGranted) {

                        locationManager.getLastLocation { location ->

                                if (location != null) {

                                    // Salva la posizione attuale.
                                    homeUiState = homeUiState.copy(
                                        currentLocation = location
                                    )

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

                    homeUiState = homeUiState.copy(
                        selectedPlace = place,
                        showPlaceInfoCard = true
                    )

                    snackbarScope.launch {
                        snackbarHostState.showSnackbar(

                            "Marker cliccato: ${place.name}"
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