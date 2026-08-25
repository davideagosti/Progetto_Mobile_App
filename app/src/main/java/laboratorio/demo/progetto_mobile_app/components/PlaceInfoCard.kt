package laboratorio.demo.progetto_mobile_app.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import laboratorio.demo.progetto_mobile_app.utils.PlaceInfo

@Composable
fun PlaceInfoCard(
    place: PlaceInfo
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null
            )

            Text(
                text = place.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = place.address,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}