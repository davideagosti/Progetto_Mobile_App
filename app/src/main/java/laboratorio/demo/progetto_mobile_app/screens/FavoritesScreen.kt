package laboratorio.demo.progetto_mobile_app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search

import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import android.net.Uri
import androidx.navigation.NavController

import laboratorio.demo.progetto_mobile_app.components.FavoritePlaceCard
import laboratorio.demo.progetto_mobile_app.utils.FavoritePlace
import laboratorio.demo.progetto_mobile_app.utils.FavoritesManager

@Composable
fun FavoritesScreen(
    navController: NavController
) {

    val favoritesManager = remember {
        FavoritesManager()
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    // Lista preferiti
    var favorites by remember {
        mutableStateOf<List<FavoritePlace>>(emptyList())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var searchText by remember {
        mutableStateOf("")
    }

    // ==========================================
    // RECUPERO PREFERITI
    // ==========================================

    LaunchedEffect(Unit) {

        favoritesManager.getFavorites { result ->

            favorites = result
            isLoading = false
        }
    }

    // ==========================================
    // FILTRO RICERCA
    // ==========================================

    val filteredFavorites = favorites.filter { favorite ->

        favorite.name.contains(
            searchText,
            ignoreCase = true
        ) ||

                favorite.address.contains(
                    searchText,
                    ignoreCase = true
                )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = "Gestisci preferiti"
            )

            // ==========================================
            // BARRA DI RICERCA
            // ==========================================

            OutlinedTextField(

                value = searchText,

                onValueChange = { text ->

                    searchText = text
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 12.dp,
                        bottom = 12.dp
                    ),

                singleLine = true,

                label = {
                    Text("Cerca nei preferiti")
                },

                leadingIcon = {

                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Cerca"
                    )
                },

                trailingIcon = {

                    if (searchText.isNotEmpty()) {

                        IconButton(
                            onClick = {
                                searchText = ""
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Cancella ricerca"
                            )
                        }
                    }
                }
            )

            // ==========================================
            // CONTENUTO
            // ==========================================

            if (isLoading) {

                CircularProgressIndicator()

            } else if (favorites.isEmpty()) {

                Text(
                    text = "Non hai ancora nessun preferito salvato."
                )

            } else if (filteredFavorites.isEmpty()) {

                Text(
                    text = "Nessun preferito trovato."
                )

            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),

                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(
                        items = filteredFavorites,
                        key = { favorite ->
                            favorite.placeId
                        }
                    ) { favorite ->

                        FavoritePlaceCard(
                            place = favorite,

                            // Eliminazione
                            onDeleted = {

                                favorites = favorites.filter {
                                    it.placeId != favorite.placeId
                                }

                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "⭐ ${favorite.name} rimosso dai preferiti"
                                    )
                                }
                            },

                            // Mostra la posizione
                            onLocationClick = {

                                navController.navigate(
                                    "home" +
                                            "?placeId=${Uri.encode(favorite.placeId)}" +
                                            "&name=${Uri.encode(favorite.name)}" +
                                            "&address=${Uri.encode(favorite.address)}" +
                                            "&latitude=${favorite.latitude}" +
                                            "&longitude=${favorite.longitude}"
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}