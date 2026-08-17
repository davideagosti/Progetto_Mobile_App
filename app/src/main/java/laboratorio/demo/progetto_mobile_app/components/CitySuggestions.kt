package laboratorio.demo.progetto_mobile_app.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class City(
    val name: String,
    val latitude: Double,
    val longitude: Double
)

val cities = listOf(
    City("Bologna", 44.4949, 11.3426),
    City("Roma", 41.9028, 12.4964),
    City("Milano", 45.4642, 9.1900),
    City("Cesena", 44.1391, 12.2432),
    City("Firenze", 43.7696, 11.2558),
    City("Venezia", 45.4408, 12.3155),
    City("Napoli", 40.8518, 14.2681),
    City("Torino", 45.0703, 7.6869)
)

@Composable
fun CitySuggestions(
    query: String,
    onCitySelected: (City) -> Unit
) {

    // Non mostra suggerimenti
    // se il campo è vuoto.
    if (query.isBlank()) {
        return
    }

    // Confronta tutto in minuscolo,
    // così Roma, ROMA e roMA sono equivalenti.
    val filteredCities = cities.filter { city ->

        city.name.lowercase().startsWith(
            query.trim().lowercase()
        )
    }

    if (filteredCities.isEmpty()) {
        return
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 6.dp
    ) {

        Column {

            filteredCities.forEach { city ->

                androidx.compose.foundation.layout.Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onCitySelected(city)
                        }
                        .padding(16.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null
                    )

                    Text(
                        text = city.name,
                        modifier = Modifier.padding(
                            start = 12.dp
                        )
                    )
                }
            }
        }
    }
}