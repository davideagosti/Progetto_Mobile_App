package laboratorio.demo.progetto_mobile_app.utils

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import android.os.Handler
import android.os.Looper

class GeocoderManager(
    private val context: Context
) {
    /**
     * Cerca un luogo tramite il testo inserito dall'utente.
     *
     * Il risultato viene restituito come LatLng.
     * Se non viene trovato nessun risultato,
     * viene restituito null.
     */
    fun search(
        query: String,
        onResult: (LatLng?) -> Unit
    ) {

        // Evita di eseguire una ricerca vuota.
        if (query.isBlank()) {
            onResult(null)
            return
        }

        // Geocoder viene eseguito su un thread separato
        // per non bloccare l'interfaccia dell'app.
        Thread {

            try {

                val geocoder = Geocoder(context)

                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocationName(
                    query.trim(),
                    1
                )

                // Controlla se ha trovato almeno
                // un risultato.
                if (!addresses.isNullOrEmpty()) {

                    val address = addresses[0]

                    val location = LatLng(
                        address.latitude,
                        address.longitude
                    )

                    // Torna sul Main Thread perché
                    // va ad aggiornare lo stato Compose.
                    Handler(
                        Looper.getMainLooper()
                    ).post {

                        onResult(location)
                    }

                } else {

                    // Nessun risultato trovato.
                    Handler(
                        Looper.getMainLooper()
                    ).post {

                        onResult(null)
                    }
                }

            } catch (e: Exception) {

                e.printStackTrace()

                // In caso di errore restituisce null.
                Handler(
                    Looper.getMainLooper()
                ).post {

                    onResult(null)
                }
            }

        }.start()
    }
}