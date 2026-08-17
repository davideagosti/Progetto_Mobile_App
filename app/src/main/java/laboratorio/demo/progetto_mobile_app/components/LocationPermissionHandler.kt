package laboratorio.demo.progetto_mobile_app.components

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun LocationPermissionHandler(
    locationPermissionGranted: Boolean,
    onPermissionResult: (Boolean) -> Unit
) {

    // Launcher utilizzato per richiedere
    // i permessi di localizzazione.
    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            // Controlla se è stato concesso
            // almeno uno dei due permessi.
            val granted =
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                        permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            onPermissionResult(granted)
        }

    // Quando il componente viene visualizzato,
    // richiede i permessi se non sono ancora concessi.
    LaunchedEffect(locationPermissionGranted) {

        if (!locationPermissionGranted) {

            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
}