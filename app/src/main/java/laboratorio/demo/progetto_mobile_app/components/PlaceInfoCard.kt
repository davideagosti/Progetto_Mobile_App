package laboratorio.demo.progetto_mobile_app.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
//import androidx.compose.material.icons.filled.Favorite
//import androidx.compose.material.icons.filled.FavoriteBorder

import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import laboratorio.demo.progetto_mobile_app.utils.FavoritePlace
import laboratorio.demo.progetto_mobile_app.utils.FavoritesManager
import laboratorio.demo.progetto_mobile_app.utils.PlaceInfo

@Composable
fun PlaceInfoCard(
    place: PlaceInfo,
    favoritesManager: FavoritesManager,
    onMessage: (String) -> Unit,
    onClose: () -> Unit
) {

    var isFavorite by remember(place.placeId) {
        mutableStateOf(false)
    }

    // Controlla su Firebase se il luogo
    // è già presente nei preferiti.
    LaunchedEffect(place.placeId) {

        favoritesManager.isFavorite(place.placeId) { favorite ->

            isFavorite = favorite
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = place.name,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = place.address,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                IconButton(
                    onClick = {

                        if (isFavorite) {

                            // ==========================
                            // RIMUOVI DAI PREFERITI
                            // ==========================

                            favoritesManager.removeFavorite(
                                place.placeId
                            ) { success ->

                                if (success) {
                                    isFavorite = false

                                    onMessage(
                                        "☆ Rimosso dai preferiti"
                                    )

                                } else {

                                    onMessage(
                                        "Impossibile rimuovere il preferito"
                                    )

                                }
                            }

                        } else {

                            // ==========================
                            // AGGIUNGI AI PREFERITI
                            // ==========================

                            val favoritePlace = FavoritePlace(
                                placeId = place.placeId,
                                name = place.name,
                                address = place.address,
                                latitude = place.location.latitude,
                                longitude = place.location.longitude
                            )

                            favoritesManager.addFavorite(
                                favoritePlace
                            ) { success ->

                                if (success) {
                                    isFavorite = true

                                    onMessage(
                                        "⭐ Aggiunto ai preferiti"
                                    )

                                } else {

                                onMessage(
                                    "Impossibile aggiungere il preferito"
                                )

                                }
                            }
                        }
                    }
                ) {

                    Icon(
                        imageVector =
                            if (isFavorite) {
                                Icons.Default.Star
                            } else {
                                Icons.Default.StarBorder
                            },

                        contentDescription =
                            if (isFavorite) {
                                "Rimuovi dai preferiti"
                            } else {
                                "Aggiungi ai preferiti"
                            }
                    )
                }

                IconButton(
                    onClick = {
                        onClose()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Chiudi"
                    )
                }
            }
        }
    }
}