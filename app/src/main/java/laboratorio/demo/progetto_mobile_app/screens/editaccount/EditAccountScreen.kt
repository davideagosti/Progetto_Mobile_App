package laboratorio.demo.progetto_mobile_app.screens.editaccount

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import laboratorio.demo.progetto_mobile_app.components.PasswordTextField

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
    // CANCELLAZIONE ACCOUNT
    // ==========================================

    var showDeleteDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var deletePassword by rememberSaveable {
        mutableStateOf("")
    }

    var deleteErrorMessage by rememberSaveable {
        mutableStateOf("")
    }

    var isDeletingAccount by rememberSaveable {
        mutableStateOf(false)
    }

    // ==========================================
    // CARICAMENTO
    // ==========================================

    var isLoading by rememberSaveable {
        mutableStateOf(true)
    }

    var isSavingAccount by rememberSaveable {
        mutableStateOf(false)
    }

    var isChangingPassword by rememberSaveable {
        mutableStateOf(false)
    }

    // ==========================================
    // CARICAMENTO DATI UTENTE DA FIRESTORE
    // ==========================================

    var datiCaricati by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(currentUser?.uid) {

        if (currentUser == null) {

            accountErrorMessage = "Nessun utente autenticato"
            isLoading = true

            return@LaunchedEffect
        }

        // Se i dati sono già stati caricati,
        // non ricarica da Firestore
        if (datiCaricati) {
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

                    datiCaricati = true
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

        isSavingAccount = true

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

                isSavingAccount  = false

                accountSuccessMessage = "Dati aggiornati correttamente"
            }
            .addOnFailureListener {

                isSavingAccount  = false

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

        isChangingPassword = true

        // ==========================================
        // RIAUTENTICAZIONE
        // ==========================================

        val email = user.email

        if (email == null) {

            isChangingPassword = false

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

                        isChangingPassword  = false

                        vecchiaPassword = ""
                        nuovaPassword = ""
                        confermaNuovaPassword = ""

                        passwordSuccessMessage =
                            "Password modificata correttamente"
                    }

                    .addOnFailureListener {

                        isChangingPassword = false

                        passwordErrorMessage =
                            "Errore durante il cambio password"
                    }
            }

            .addOnFailureListener {

                isChangingPassword = false

                passwordErrorMessage =
                    "La password attuale non è corretta"
            }
    }


    // ==========================================
    // RIMOZIONE ACCOUNT
    // ==========================================

    fun deleteAccount() {

        val user = auth.currentUser

        if (user == null) {

            deleteErrorMessage =
                "Nessun utente autenticato"

            return
        }

        if (deletePassword.isBlank()) {

            deleteErrorMessage =
                "Inserisci la password attuale"

            return
        }

        val email = user.email

        if (email == null) {

            deleteErrorMessage =
                "Impossibile recuperare l'email dell'account"

            return
        }

        deleteErrorMessage = ""
        isDeletingAccount = true

        // ==========================================
        // RIAUTENTICAZIONE
        // ==========================================

        val credential =
            EmailAuthProvider.getCredential(
                email,
                deletePassword
            )

        user.reauthenticate(credential)

            .addOnSuccessListener {

                // ==========================================
                // PASSWORD CORRETTA
                // ==========================================

                // Prima eliminia il documento Firestore

                firestore
                    .collection("users")
                    .document(user.uid)
                    .delete()

                    .addOnSuccessListener {

                        // ==========================================
                        // ELIMINA ACCOUNT FIREBASE AUTH
                        // ==========================================

                        user.delete()

                            .addOnSuccessListener {

                                isDeletingAccount = false

                                deletePassword = ""
                                deleteErrorMessage = ""
                                showDeleteDialog = false

                                // ==========================================
                                // TORNA AL LOGIN
                                // ==========================================

                                navController.navigate("login") {

                                    popUpTo(0) {
                                        inclusive = true
                                    }

                                }
                            }

                            .addOnFailureListener {

                                isDeletingAccount = false

                                deleteErrorMessage =
                                    "Errore durante l'eliminazione dell'account"
                            }
                    }

                    .addOnFailureListener {

                        isDeletingAccount = false

                        deleteErrorMessage =
                            "Errore durante l'eliminazione dei dati dell'account"
                    }
            }

            .addOnFailureListener {

                isDeletingAccount = false

                deleteErrorMessage = "La password attuale non è corretta"
            }
    }

    // Messaggio di conferma Delete Account
    if (showDeleteDialog) {

        AlertDialog(

            onDismissRequest = {

                if (!isDeletingAccount) {

                    showDeleteDialog = false
                    deletePassword = ""
                    deleteErrorMessage = ""
                }
            },

            title = {
                Text(
                    text = "Eliminare l'account?"
                )
            },

            text = {

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding()
                ) {

                    Text(
                        text =
                        "Questa operazione è permanente. " +
                        "Tutti i dati dell'account verranno eliminati."
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    PasswordTextField(
                        value = deletePassword,

                        onValueChange = {value: String ->
                            deletePassword = value
                            deleteErrorMessage = ""
                        },

                        label = "Password attuale",

                        modifier = Modifier.fillMaxWidth()
                    )

                    if (deleteErrorMessage.isNotEmpty()) {

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = deleteErrorMessage,

                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },

            confirmButton = {

                Button(

                    onClick = {
                        deleteAccount()
                    },

                    enabled =
                    !isDeletingAccount &&
                            deletePassword.isNotBlank()
                ) {

                    if (isDeletingAccount) {

                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )

                    } else {

                        Text(
                            text = "Elimina definitivamente"
                        )
                    }
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        if (!isDeletingAccount) {

                            showDeleteDialog = false
                            deletePassword = ""
                            deleteErrorMessage = ""
                        }
                    },

                    enabled = !isDeletingAccount
                ) {

                    Text("Annulla")
                }
            }
        )
    }

    // ==========================================
    // INTERFACCIA
    // ==========================================

    val landscape = isLandscape()

    if (landscape) {

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

            isSavingAccount = isSavingAccount,
            isChangingPassword = isChangingPassword,

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
            },

            onDeleteAccountClick = {
                deleteErrorMessage = ""
                deletePassword = ""
                showDeleteDialog = true
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

            isSavingAccount = isSavingAccount,
            isChangingPassword = isChangingPassword,

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
            },

            onDeleteAccountClick = {
                deleteErrorMessage = ""
                deletePassword = ""
                showDeleteDialog = true
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