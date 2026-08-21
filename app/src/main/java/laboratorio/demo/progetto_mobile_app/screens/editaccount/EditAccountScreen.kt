package laboratorio.demo.progetto_mobile_app.screens.editaccount

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.google.firebase.firestore.SetOptions
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import laboratorio.demo.progetto_mobile_app.components.isLandscape

@Composable
fun EditAccountScreen(
    navController: NavController
) {

    // ==========================================
    // FIREBASE
    // ==========================================

    val auth = remember {
        FirebaseAuth.getInstance()
    }

    val firestore = remember {
        FirebaseFirestore.getInstance()
    }

    val currentUser = auth.currentUser

    // ==========================================
    // DATI ACCOUNT
    // ==========================================

    var nome by rememberSaveable {
        mutableStateOf("")
    }

    var cognome by rememberSaveable {
        mutableStateOf("")
    }

    // ==========================================
    // DATI PASSWORD
    // ==========================================

    var vecchiaPassword by rememberSaveable {
        mutableStateOf("")
    }

    var nuovaPassword by rememberSaveable {
        mutableStateOf("")
    }

    var confermaNuovaPassword by rememberSaveable {
        mutableStateOf("")
    }

    // ==========================================
    // MESSAGGI DATI ACCOUNT
    // ==========================================

    var accountErrorMessage by rememberSaveable {
        mutableStateOf("")
    }

    var accountSuccessMessage by rememberSaveable {
        mutableStateOf("")
    }

    // ==========================================
    // MESSAGGI PASSWORD
    // ==========================================

    var passwordErrorMessage by rememberSaveable {
        mutableStateOf("")
    }

    var passwordSuccessMessage by rememberSaveable {
        mutableStateOf("")
    }

    // ==========================================
    // CARICAMENTO
    // ==========================================

    var isLoading by rememberSaveable {
        mutableStateOf(true)
    }

    // ==========================================
    // CARICAMENTO DATI UTENTE DA FIRESTORE
    // ==========================================

    LaunchedEffect(currentUser?.uid) {

        if (currentUser == null) {

            accountErrorMessage = "Nessun utente autenticato"
            isLoading = false

            return@LaunchedEffect
        }

        firestore
            .collection("users")
            .document(currentUser.uid)
            .get()
            .addOnSuccessListener { document ->

                if (document.exists()) {

                    nome =
                        document.getString("nome") ?: ""

                    cognome =
                        document.getString("cognome") ?: ""
                } else {

                    accountErrorMessage = "Dati account non trovati"
                }

                // Termina il caricamento
                isLoading = false
            }
            .addOnFailureListener {

                accountErrorMessage = "Errore nel caricamento dei dati"

                isLoading = false
            }
    }


    // ==========================================
    // SALVATAGGIO NOME E COGNOME
    // ==========================================

    fun saveAccount() {

        val user = auth.currentUser

        if (user == null) {

            accountErrorMessage = "Nessun utente autenticato"

            return
        }

        // --------------------------
        // VALIDAZIONE
        // --------------------------

        if (nome.isBlank()) {

            accountErrorMessage = "Inserisci il nome"
            return
        }

        if (cognome.isBlank()) {

            accountErrorMessage = "Inserisci il cognome"
            return
        }

        accountErrorMessage = ""
        accountSuccessMessage = ""

        isLoading = true

        val userData = hashMapOf<String, Any>(
            "nome" to nome.trim(),
            "cognome" to cognome.trim()
        )


        // --------------------------
        // SALVATAGGIO FIRESTORE
        // --------------------------

        firestore
            .collection("users")
            .document(user.uid)
            .update(userData)
            .addOnSuccessListener {

                isLoading = false

                accountSuccessMessage = "Dati aggiornati correttamente"
            }
            .addOnFailureListener {

                isLoading = false

                accountErrorMessage = "Errore durante il salvataggio"
            }
    }

    // ==========================================
    // CAMBIO PASSWORD
    // ==========================================

    fun changePassword() {

        val user = auth.currentUser

        if (user == null) {

            passwordErrorMessage = "Nessun utente autenticato"

            return
        }

        // --------------------------
        // CONTROLLI
        // --------------------------

        if (vecchiaPassword.isBlank()) {

            passwordErrorMessage = "Inserisci la password attuale"

            return
        }

        if (nuovaPassword.isBlank()) {

            passwordErrorMessage = "Inserisci la nuova password"

            return
        }

        if (nuovaPassword.length < 8) {

            passwordErrorMessage =
                "La nuova password deve contenere almeno 8 caratteri"

            return
        }

        if (nuovaPassword != confermaNuovaPassword) {

            passwordErrorMessage = "Le nuove password non coincidono"

            return
        }

        // Pulisce solamente i messaggi della password
        passwordErrorMessage = ""
        passwordSuccessMessage = ""

        isLoading = true

        // ==========================================
        // RIAUTENTICAZIONE
        // ==========================================

        val email = user.email

        if (email == null) {

            isLoading = false

            passwordErrorMessage =
                "Impossibile recuperare l'email dell'account"

            return
        }

        // --------------------------
        // CREDENZIALI ATTUALI
        // --------------------------

        val credential =
            EmailAuthProvider.getCredential(
                email,
                vecchiaPassword
            )

        // --------------------------
        // RICONFERMA IDENTITÀ
        // --------------------------

        user.reauthenticate(credential)

            .addOnSuccessListener {

                // --------------------------
                // PASSWORD ATTUALE CORRETTA
                // --------------------------

                user.updatePassword(nuovaPassword)

                    .addOnSuccessListener {

                        isLoading = false

                        vecchiaPassword = ""
                        nuovaPassword = ""
                        confermaNuovaPassword = ""

                        passwordSuccessMessage =
                            "Password modificata correttamente"
                    }

                    .addOnFailureListener {

                        isLoading = false

                        passwordErrorMessage =
                            "Errore durante il cambio password"
                    }
            }

            .addOnFailureListener {

                isLoading = false

                passwordErrorMessage =
                    "La password attuale non è corretta"
            }
    }


    // ==========================================
    // INTERFACCIA
    // ==========================================

    if (isLandscape()) {

        EditAccountLandscape(

            navController = navController,

            nome = nome,
            cognome = cognome,
            vecchiaPassword = vecchiaPassword,
            nuovaPassword = nuovaPassword,
            confermaNuovaPassword = confermaNuovaPassword,

            accountErrorMessage = accountErrorMessage,
            accountSuccessMessage = accountSuccessMessage,

            passwordErrorMessage = passwordErrorMessage,
            passwordSuccessMessage = passwordSuccessMessage,

            isLoading = isLoading,

            onNomeChange = {
                nome = it
            },

            onCognomeChange = {
                cognome = it
            },

            onVecchiaPasswordChange = {
                vecchiaPassword = it
            },

            onNuovaPasswordChange = {
                nuovaPassword = it
            },

            onConfermaNuovaPasswordChange = {
                confermaNuovaPassword = it
            },

            onSaveClick = {
                saveAccount()
            },

            onChangePasswordClick = {
                changePassword()
            }
        )

    } else {

        EditAccountPortrait(

            navController = navController,

            nome = nome,
            cognome = cognome,
            vecchiaPassword = vecchiaPassword,
            nuovaPassword = nuovaPassword,
            confermaNuovaPassword = confermaNuovaPassword,

            accountErrorMessage = accountErrorMessage,
            accountSuccessMessage = accountSuccessMessage,

            passwordErrorMessage = passwordErrorMessage,
            passwordSuccessMessage = passwordSuccessMessage,

            isLoading = isLoading,

            onNomeChange = {
                nome = it
            },

            onCognomeChange = {
                cognome = it
            },

            onVecchiaPasswordChange = {
                vecchiaPassword = it
            },

            onNuovaPasswordChange = {
                nuovaPassword = it
            },

            onConfermaNuovaPasswordChange = {
                confermaNuovaPassword = it
            },

            onSaveClick = {
                saveAccount()
            },

            onChangePasswordClick = {
                changePassword()
            }
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)

@Composable
fun EditAccountScreenPreview() {
    MaterialTheme {
        EditAccountScreen(
            navController = rememberNavController()
        )
    }
}