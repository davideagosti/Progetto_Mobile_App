package laboratorio.demo.progetto_mobile_app.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner

import androidx.core.content.ContextCompat

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

import kotlinx.coroutines.delay

/**
 *  Gestione del permesso della posizione
 * */
@Composable
fun LocationPermissionHandler(
    onPermissionResult: (Boolean) -> Unit,
    onLocationEnabledResult: (Boolean) -> Unit
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Controllo permesso
    fun checkLocationPermission(): Boolean {

        val fineLocation =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        val coarseLocation =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        return fineLocation || coarseLocation
    }

    // CONTROLLO POSIZIONE DISPOSITIVO
    fun checkLocationEnabled(): Boolean {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            locationManager.isLocationEnabled
        } else {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }
    }

    // Launcher per la richiesta dei permessi Android
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

            // Controlla anche se la posizione del dispositivo è attiva
            onLocationEnabledResult(checkLocationEnabled())
        }

    /*
     * Controllo iniziale.
     *
     * Se il permesso non è già disponibile,
     * viene mostrato il popup Android.
     */
    LaunchedEffect(Unit) {

        val permissionGranted = checkLocationPermission()
        val locationEnabled = checkLocationEnabled()

        onPermissionResult(permissionGranted)
        onLocationEnabledResult(locationEnabled)

        // Mostra il popup Android se manca il permesso
        if (!permissionGranted) {

            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    /*
    * Controlla nuovamente il permesso ogni volta
    * che l'app torna in primo piano.
    * */
    DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->

            if (event == Lifecycle.Event.ON_RESUME) {

                val permissionGranted = checkLocationPermission()
                val locationEnabled = checkLocationEnabled()

                onPermissionResult(permissionGranted)
                onLocationEnabledResult(locationEnabled)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // CONTROLLO PERIODICO
    LaunchedEffect(Unit) {

        while (true) {

            val permissionGranted =
                checkLocationPermission()

            val locationEnabled =
                checkLocationEnabled()

            onPermissionResult(permissionGranted)
            onLocationEnabledResult(locationEnabled)

            // Controlla ogni 500 ms
            delay(500)
        }
    }
}