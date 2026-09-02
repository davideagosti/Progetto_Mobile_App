package laboratorio.demo.progetto_mobile_app.model

import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.gms.maps.model.LatLng

data class SearchState(
    val text: String = "",                                          // Testo inserito nella barra di ricerca
    val location: LatLng? = null,                                   // Posizione trovata dalla ricerca Null significa
                                                                    // che non è stata ancora trovata nessuna posizione.
    val suggestions: List<AutocompletePrediction> = emptyList(),    // Indica se la tendina dei suggerimenti deve essere visibile.
    val showSuggestions: Boolean = false
)