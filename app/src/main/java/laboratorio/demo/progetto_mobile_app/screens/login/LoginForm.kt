package laboratorio.demo.progetto_mobile_app.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import laboratorio.demo.progetto_mobile_app.navigation.Routes
import laboratorio.demo.progetto_mobile_app.components.PasswordTextField

@Composable
fun LoginForm(
    navController: NavController,

    email: String,
    password: String,
    errorMessage: String,

    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onErrorChange: (String) -> Unit,
    onLogin: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF2F2F2)
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // =========================
            // TITOLO
            // =========================

            Text(
                text = "Login account",

                fontSize = 24.sp,

                fontWeight = FontWeight.Bold
            )

            // =========================
            // EMAIL
            // =========================

            OutlinedTextField(
                value = email,

                onValueChange = {
                    onEmailChange(it)
                },

                label = {
                    Text("Email")
                },

                modifier = Modifier.fillMaxWidth()
            )

            // =========================
            // PASSWORD
            // =========================

            //OutlinedTextField(
            PasswordTextField(
                value = password,

                onValueChange = onPasswordChange,

                label = "Password",

                modifier = Modifier.fillMaxWidth()
            )

            // =========================
            // ACCEDI
            // =========================

            Button(
                onClick = {
                    // TODO: Login
                    onErrorChange("")

                    when {

                        email.isBlank() -> {

                            onErrorChange(
                                "Inserisci l'email"
                            )
                        }

                        password.isBlank() -> {

                            onErrorChange(
                                "Inserisci la password"
                            )
                        }

                        password.length < 8 -> {

                            onErrorChange(
                                "La password deve contenere almeno 8 caratteri"
                            )
                        }

                        else -> {

                            // Login corretto
                            // TODO:
                            // collegamento database/server

                            onLogin()
                            /*
                            navController.navigate(
                                Routes.Home.route
                            ) {

                                popUpTo(
                                    Routes.Home.route
                                ) {
                                    inclusive = true
                                }

                                launchSingleTop = true

                                restoreState = true
                            }*/
                        }
                    }
                },

                modifier = Modifier.fillMaxWidth()
            ) {

                Text("Accedi")
            }

            // =========================
            // ERRORE
            // =========================

            if (errorMessage.isNotEmpty()) {

                Text(
                    text = errorMessage,

                    color = MaterialTheme
                        .colorScheme
                        .error,

                    fontSize = 14.sp
                )
            }

            // =========================
            // REGISTRAZIONE
            // =========================

            TextButton(
                onClick = {
                    // TODO Login
                    navController.navigate(
                        Routes.Register.route
                    )
                },

                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    "Non hai un account? Registrati"
                )
            }
        }
    }
}
