package laboratorio.demo.progetto_mobile_app.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FavoritesManager {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    // =========================================================
    // AGGIUNGI PREFERITO
    // =========================================================

    fun addFavorite(
        place: FavoritePlace,
        onResult: (Boolean) -> Unit
    ) {

        val user = auth.currentUser

        // Nessun utente autenticato.
        if (user == null) {
            onResult(false)
            return
        }

        firestore
            .collection("users")
            .document(user.uid)
            .collection("favorites")
            .document(place.placeId)
            .set(place)
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }

    // =========================================================
    // RIMUOVI PREFERITO
    // =========================================================

    fun removeFavorite(
        placeId: String,
        onResult: (Boolean) -> Unit
    ) {

        val user = auth.currentUser

        if (user == null) {
            onResult(false)
            return
        }

        firestore
            .collection("users")
            .document(user.uid)
            .collection("favorites")
            .document(placeId)
            .delete()
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }

    // =========================================================
    // CONTROLLA SE È GIÀ UN PREFERITO
    // =========================================================

    fun isFavorite(
        placeId: String,
        onResult: (Boolean) -> Unit
    ) {

        val user = auth.currentUser

        if (user == null) {
            onResult(false)
            return
        }

        firestore
            .collection("users")
            .document(user.uid)
            .collection("favorites")
            .document(placeId)
            .get()
            .addOnSuccessListener { document ->

                onResult(document.exists())
            }
            .addOnFailureListener {

                onResult(false)
            }
    }

    // =========================================================
    // RECUPERA TUTTI I PREFERITI
    // =========================================================

    fun getFavorites(
        onResult: (List<FavoritePlace>) -> Unit
    ) {

        val user = auth.currentUser

        if (user == null) {
            onResult(emptyList())
            return
        }

        firestore
            .collection("users")
            .document(user.uid)
            .collection("favorites")
            .get()
            .addOnSuccessListener { result ->

                val favorites = result.documents.mapNotNull { document ->

                    document.toObject(FavoritePlace::class.java)
                }

                onResult(favorites)
            }
            .addOnFailureListener {

                onResult(emptyList())
            }
    }
}