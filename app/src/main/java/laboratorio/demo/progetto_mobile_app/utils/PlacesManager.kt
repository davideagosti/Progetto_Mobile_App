package laboratorio.demo.progetto_mobile_app.utils

import android.content.Context
import com.google.android.gms.maps.model.LatLng

import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.AutocompletePrediction

import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest

data class PlaceInfo(
    val placeId: String,
    val name: String,
    val address: String,
    val location: LatLng
)

class PlacesManager (
    context: Context
) {
    private val placesClient: PlacesClient =
        Places.createClient(context)


    // Cerca suggerimenti mentre l'utente digita.
    fun getSuggestions(
        query: String,
        onResult: (List<AutocompletePrediction>) -> Unit
    ) {

        if (query.isBlank()) {
            onResult(emptyList())
            return
        }

        val request =
            FindAutocompletePredictionsRequest
                .builder()
                .setQuery(query.trim())
                .build()

        placesClient
            .findAutocompletePredictions(request)
            .addOnSuccessListener { response ->

                onResult(response.autocompletePredictions)

            }
            .addOnFailureListener {

                onResult(emptyList())

            }
    }


    // Recupera le coordinate di un luogo selezionato.
    fun getPlaceLocation(
        prediction: AutocompletePrediction,
        onResult: (PlaceInfo?) -> Unit
    ) {

        val placeId = prediction.placeId

        val placeFields = listOf(
            Place.Field.ID,
            Place.Field.LAT_LNG,
            Place.Field.NAME,
            Place.Field.ADDRESS
        )

        val request = FetchPlaceRequest
            .builder(
                placeId,
                placeFields
            )
            .build()

        placesClient
            .fetchPlace(request)
            .addOnSuccessListener { response ->

                val place = response.place

                val latLng = place.latLng

                if (latLng != null) {

                    val placeInfo = PlaceInfo(
                        placeId = placeId,
                        name = place.name ?: "Luogo",
                        address = place.address ?: "Indirizzo non disponibile",
                        location = latLng
                    )

                    onResult(placeInfo)

                } else {

                    onResult(null)
                }

            }
            .addOnFailureListener {

                onResult(null)

            }
    }
}