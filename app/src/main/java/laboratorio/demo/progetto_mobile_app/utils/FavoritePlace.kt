package laboratorio.demo.progetto_mobile_app.utils

/**
 * Rappresenta un luogo salvato dall'utente tra i preferiti.
 *
 * Contiene le informazioni necessarie per poterlo visualizzare
 * e riutilizzare successivamente.
 */
data class FavoritePlace(
    val placeId: String = "",
    val name: String = "",
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)