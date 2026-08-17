package laboratorio.demo.progetto_mobile_app.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.MainScope

class LocationManager (
    private val context: Context
) {
    private val fusedLocationClient : FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    /**
     * Controlla se l'app dispone di un permesso
     * per accedere alla posizione.
     */
    fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||

        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Recupera l'ultima posizione conosciuta
     * del dispositivo.
     */
    fun getLastLocation (
        onLocationReceived: (Location?) -> Unit
    ) {
        // Se non abbiamo il permesso,
        // non possiamo accedere alla posizione.
        // Controlla se l'utente abbia concesso
        // un permesso di localizzazione.
        if (!hasLocationPermission()) {

            onLocationReceived(null)
            return
        }

        try {

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->

                    // Restituiamo la posizione trovata.
                    onLocationReceived(location)
                }
                .addOnFailureListener {

                    // Se il recupero della posizione fallisce,
                    // restituisce null.
                    onLocationReceived(null)
                }

        } catch (e: SecurityException) {

            // Protezione aggiuntiva nel caso in cui
            // il permesso venga revocato mentre l'app è in esecuzione.
            onLocationReceived(null)
        }

    }
}