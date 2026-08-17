package laboratorio.demo.progetto_mobile_app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MapControls (
    onZoomIn: () -> Unit,
    onZoomOut: () -> Unit,
    onMyLocation: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {

        // =====================================
        // CONTROLLI ZOOM
        // =====================================

        Surface(
            modifier = Modifier.size(48.dp),
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 4.dp
        ) {

//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {

                // Zoom +
                IconButton(
                    onClick = onZoomIn,
                    modifier = Modifier.size(48.dp)
                ) {
                    Text(
                        text = "+",
                        fontSize = 24.sp
                    )
                }
            //}
        }

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        // ==============================
        // ZOOM -
        // ==============================

        Surface(
            modifier = Modifier.size(48.dp),
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 4.dp
        ) {

            IconButton(
                onClick = onZoomOut,
                modifier = Modifier.size(48.dp)
            ) {
                Text(
                    text = "−",
                    fontSize = 24.sp
                )
            }
        }

        // =====================================
        // SPAZIO TRA ZOOM E POSIZIONE
        // =====================================

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // =====================================
        // LA MIA POSIZIONE
        // =====================================

        Surface(
            modifier = Modifier.size(48.dp),
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 4.dp
        ) {

            IconButton(
                onClick = onMyLocation,
                modifier = Modifier.size(48.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = "La mia posizione"
                )
            }
        }
    }
}