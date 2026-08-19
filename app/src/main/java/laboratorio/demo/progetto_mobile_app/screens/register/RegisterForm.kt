package laboratorio.demo.progetto_mobile_app.screens.register

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import laboratorio.demo.progetto_mobile_app.navigation.Routes
import laboratorio.demo.progetto_mobile_app.components.PasswordTextField

@Composable
fun RegisterForm (
    navController: NavController,

    email: String,
    nome: String,
    cognome: String,
    password: String,
    confermaPassword: String,
    errorMessage: String,

    onEmailChange: (String) -> Unit,
    onNomeChange: (String) -> Unit,
    onCognomeChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfermaPasswordChange: (String) -> Unit,
    onErrorChange: (String) -> Unit,
    onRegister: () -> Unit
){
// CARD FORM
    Card(
        modifier = Modifier
            .fillMaxWidth(),
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
//                        .fillMaxSize()
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "Registra e crea il tuo account",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
                //style = MaterialTheme.typography.headlineMedium
                //style = MaterialTheme.typography.bodyLarge
            )

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                //onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = nome,
                onValueChange = onNomeChange,
                //onValueChange = { nome = it },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = cognome,
                onValueChange = onCognomeChange,
                //onValueChange = { cognome = it },
                label = { Text("Cognome") },
                modifier = Modifier.fillMaxWidth()
            )

            //OutlinedTextField(
            PasswordTextField(
                value = password,
                //onValueChange = onPasswordChange,
                onValueChange = onPasswordChange,
                //onValueChange = { password = it },
                //label = { Text("Password") },
                label = "Password",
                //visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            //OutlinedTextField(
            PasswordTextField(
                value = confermaPassword,
                /*onValueChange = {
                confermaPassword = it
            },
            */
                //onValueChange = onConfermaPasswordChange,
                onValueChange = onConfermaPasswordChange,
//                label = {
//                    Text("Conferma Password")
//                },
                label = "Conferma password",
                //visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    // TODO: registrazione
                    onErrorChange("")
                    //errorMessage = ""

                    when {
                        email.isBlank() ||
                            nome.isBlank() ||
                            cognome.isBlank() ||
                            password.isBlank() ||
                            confermaPassword.isBlank() -> {

                                onErrorChange("Compila tutti i campi")
                                //errorMessage = "Compila tutti i campi"
                        }

                        !Patterns.EMAIL_ADDRESS
                            .matcher(email)
                            .matches() -> {

                            onErrorChange("Inserisci una email valida")
                            //errorMessage = "Inserisci una email valida"
                        }

                        password.length < 8 -> {
                            onErrorChange("La password deve contenere almeno 8 caratteri")
                            /*errorMessage =
                            "La password deve contenere almeno 8 caratteri"*/
                        }

                        password != confermaPassword -> {
                            onErrorChange("Le password non coincidono")
                            /*errorMessage =
                            "Le password non coincidono"*/
                        }

                        else -> {
                            // Registrazione corretta
                            // TODO salvataggio account
                            onRegister()
                            /*
                            navController.navigate(Routes.Home.route) {

                                popUpTo(Routes.Home.route) {
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
                Text("Registrati")
            }

            if (errorMessage.isNotEmpty()) {

                Text(
                    text = errorMessage,
                    //color = Color.Red,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp
                )

            }

            TextButton(
                onClick = {
                    // TODO Login
                    navController.navigate(Routes.Login.route)

                }
            ) {
                Text("Hai già un account? Accedi")
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun RegisterForm() {
    MaterialTheme {
        RegisterForm()
    }
}