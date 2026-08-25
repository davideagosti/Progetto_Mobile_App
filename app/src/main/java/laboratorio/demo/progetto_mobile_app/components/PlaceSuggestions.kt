package laboratorio.demo.progetto_mobile_app.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.google.android.libraries.places.api.model.AutocompletePrediction

@Composable
fun PlaceSuggestions(
    suggestions: List<AutocompletePrediction>,
    onPlaceSelected: (AutocompletePrediction) -> Unit
) {

    // Non mostra nulla se non ci sono suggerimenti.
    if (suggestions.isEmpty()) {
        return
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 6.dp
    ) {

        Column {

            suggestions.forEach { prediction ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onPlaceSelected(prediction)
                        }
                        .padding(16.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Luogo"
                    )

                    Column(
                        modifier = Modifier.padding(start = 12.dp)
                    ) {

                        // Nome principale del luogo
                        Text(
                            text = prediction.getPrimaryText(null).toString()
                        )

                        // Indirizzo / descrizione secondaria
                        Text(
                            text = prediction.getSecondaryText(null).toString(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}