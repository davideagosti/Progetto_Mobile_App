package laboratorio.demo.progetto_mobile_app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Star

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import laboratorio.demo.progetto_mobile_app.utils.FavoritePlace
import laboratorio.demo.progetto_mobile_app.utils.FavoritesManager

@Composable
fun FavoritePlaceCard(
    place: FavoritePlace,
    onDeleted: () -> Unit = {},
    onLocationClick: () -> Unit = {}
) {

    // ==========================================
    // GESTIONE POPUP DI CONFERMA ELIMINAZIONE
    // ==========================================

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    // ==========================================
    // FAVORITES MANAGER
    // ==========================================

    val favoritesManager = remember {
        FavoritesManager()
    }

    // ==========================================
    // CARD
    // ==========================================

    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            // ==========================================
            // INFORMAZIONI LUOGO
            // ==========================================

            Column(
                modifier = Modifier.weight(1f),

                verticalArrangement =
                    Arrangement.spacedBy(4.dp)
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Preferito"
                    )

                    Text(
                        text = place.name,
                        style =
                            MaterialTheme.typography.titleMedium,

                        modifier = Modifier
                            .padding(start = 8.dp)
                    )
                }

                Text(
                    text = place.address,
                    style =
                        MaterialTheme.typography.bodyMedium
                )
            }

            // ==========================================
            // VAI ALLA POSIZIONE
            // ==========================================

            IconButton(
                onClick = {

                    onLocationClick()
                }
            ) {

                Icon(
                    imageVector = Icons.Default.Map,

                    contentDescription =
                        "Vai alla posizione"
                )
            }

            // ==========================================
            // PULSANTE ELIMINA
            // ==========================================

            IconButton(
                onClick = {
                    showDeleteDialog = true
                }
            ) {

                Icon(
                    imageVector = Icons.Default.Delete,

                    contentDescription =
                        "Rimuovi dai preferiti"
                )
            }
        }
    }

    // ==========================================
    // POPUP CONFERMA ELIMINAZIONE
    // ==========================================

    if (showDeleteDialog) {

        AlertDialog(

            onDismissRequest = {
                showDeleteDialog = false
            },

            // ======================================
            // TITOLO
            // ======================================

            title = {

                Text(
                    text = "Rimuovere dai preferiti?"
                )
            },

            // ======================================
            // TESTO
            // ======================================

            text = {

                Text(
                    text =
                        "Vuoi rimuovere \"${place.name}\" " +
                                "dai tuoi preferiti?"
                )
            },

            // ======================================
            // CONFERMA
            // ======================================

            confirmButton = {

                TextButton(

                    onClick = {

                        favoritesManager.removeFavorite(
                            place.placeId
                        ) { success ->

                            if (success) {

                                // Chiude il popup
                                showDeleteDialog = false

                                // Avvisa FavoritesScreen
                                // che il luogo è stato eliminato.
                                onDeleted()
                            }
                        }
                    }
                ) {

                    Text(
                        text = "Rimuovi"
                    )
                }
            },

            // ======================================
            // ANNULLA
            // ======================================

            dismissButton = {

                TextButton(

                    onClick = {

                        showDeleteDialog = false
                    }
                ) {

                    Text(
                        text = "Annulla"
                    )
                }
            }
        )
    }
}