package laboratorio.demo.progetto_mobile_app.screens.register

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable

import androidx.navigation.NavController
import laboratorio.demo.progetto_mobile_app.navigation.Routes
import laboratorio.demo.progetto_mobile_app.components.isLandscape
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {

    var email by rememberSaveable { mutableStateOf("") }
    var nome by rememberSaveable { mutableStateOf("") }
    var cognome by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confermaPassword by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    val auth = remember {
        FirebaseAuth.getInstance()
    }

    val firestore = remember {
        FirebaseFirestore.getInstance()
    }

    fun registerUser() {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    val user = auth.currentUser

                    if (user != null) {

                        val userData = hashMapOf(
                            "nome" to nome,
                            "cognome" to cognome,
                            "email" to email
                        )

                        firestore
                            .collection("users")
                            .document(user.uid)
                            .set(userData)
                            .addOnSuccessListener {

                                navController.navigate(Routes.Home.route) {
                                    popUpTo(Routes.Login.route) {
                                        inclusive = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                            .addOnFailureListener {

                                errorMessage =
                                    "Account creato, ma errore nel salvataggio dei dati"
                            }
                    }

                } else {

                    errorMessage =
                        task.exception?.message
                            ?: "Errore durante la registrazione"
                }
            }
    }

    if (isLandscape()) {

        RegisterLandscape(
            navController = navController,

            email = email,
            nome = nome,
            cognome = cognome,
            password = password,
            confermaPassword = confermaPassword,
            errorMessage = errorMessage,

            onEmailChange = { email = it },
            onNomeChange = { nome = it },
            onCognomeChange = { cognome = it },
            onPasswordChange = { password = it },
            onConfermaPasswordChange = { confermaPassword = it },
            onErrorChange = { errorMessage = it },
            onRegister = { registerUser() }
        )

    } else {

        RegisterPortrait(
            navController = navController,

            email = email,
            nome = nome,
            cognome = cognome,
            password = password,
            confermaPassword = confermaPassword,
            errorMessage = errorMessage,

            onEmailChange = { email = it },
            onNomeChange = { nome = it },
            onCognomeChange = { cognome = it },
            onPasswordChange = { password = it },
            onConfermaPasswordChange = { confermaPassword = it },
            onErrorChange = { errorMessage = it },
            onRegister = { registerUser() }
        )
    }
}
