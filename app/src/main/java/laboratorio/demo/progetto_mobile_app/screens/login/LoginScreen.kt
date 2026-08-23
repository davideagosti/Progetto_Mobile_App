package laboratorio.demo.progetto_mobile_app.screens.login

import androidx.compose.runtime.Composable

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.compose.rememberNavController
import laboratorio.demo.progetto_mobile_app.navigation.Routes
import laboratorio.demo.progetto_mobile_app.components.isLandscape


@Composable
fun LoginScreen(navController: NavController) {
    var email by rememberSaveable  { mutableStateOf("") }
    var password by rememberSaveable  { mutableStateOf("") }
    var errorMessage by rememberSaveable  { mutableStateOf("") }

    val auth = remember {
        FirebaseAuth.getInstance()
    }

    fun loginUser() {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    // Login effettuato correttamente
                    navController.navigate(Routes.Home.route) {

                        popUpTo(Routes.Login.route) {
                            inclusive = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }

                } else {

                    errorMessage =
                        task.exception?.message
                            ?: "Email o password non corrette"
                }
            }
    }

    val landscape = isLandscape()

    if (landscape) {
        LoginLandscape(
            navController = navController,

            email = email,
            password = password,
            errorMessage = errorMessage,

            onEmailChange = { email = it },
            onPasswordChange = { password = it },
            onErrorChange = { errorMessage = it },
            onLogin = { loginUser() }
        )
    } else {
        LoginPortrait(
            navController = navController,

            email = email,
            password = password,
            errorMessage = errorMessage,

            onEmailChange = { email = it },
            onPasswordChange = { password = it },
            onErrorChange = { errorMessage = it },
            onLogin = { loginUser() }
        )
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)

@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            navController = rememberNavController()
        )
    }
}
