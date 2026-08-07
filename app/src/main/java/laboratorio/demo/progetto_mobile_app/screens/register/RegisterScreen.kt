package laboratorio.demo.progetto_mobile_app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex

import laboratorio.demo.progetto_mobile_app.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import laboratorio.demo.progetto_mobile_app.components.BackButton
import laboratorio.demo.progetto_mobile_app.navigation.Routes
import android.content.res.Configuration
import androidx.compose.ui.platform.LocalConfiguration
import laboratorio.demo.progetto_mobile_app.components.isLandscape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var cognome by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confermaPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        BackButton(
//            navController = navController,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .zIndex(1f),
            onClick = {
                navController.popBackStack(
                    Routes.Home,
                    false
                )
            }

//            onClick = {
//                navController.navigate(Routes.Home) {
//                    popUpTo(Routes.Home) {
//                        inclusive = false
//                    }
//                    launchSingleTop = true
//                    restoreState = true
//                }
//            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                //.fillMaxWidth()
                //.wrapContentHeight()
                .verticalScroll(rememberScrollState())
                .padding(start = 20.dp,
                    end = 20.dp,
                    top = 70.dp,
                    bottom = 20.dp
                ),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Top
        ) {

//            Row(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                BackButton(navController)
//            }

            // Logo
            Image(
                painter = painterResource(R.drawable.google_maps_image),
                contentDescription = null,
                modifier = Modifier.size(90.dp)
            )

    //        Text(
    //            text = "Registrazione"
    //        )

            Spacer(modifier = Modifier.height(20.dp))

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
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                        //style = MaterialTheme.typography.headlineMedium
                        //style = MaterialTheme.typography.bodyLarge
                    )

    //        Text(
    //            text = "Registrazione",
    //            style = MaterialTheme.typography.headlineMedium
    //        )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text("Nome") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = cognome,
                        onValueChange = { cognome = it },
                        label = { Text("Cognome") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = confermaPassword,
                        onValueChange = {
                            confermaPassword = it
                        },
                        label = {
                            Text("Conferma Password")
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            // TODO: registrazione
                            errorMessage = ""

                            when {
                                email.isBlank() ||
                                        nome.isBlank() ||
                                        cognome.isBlank() ||
                                        password.isBlank() ||
                                        confermaPassword.isBlank() -> {

                                    errorMessage = "Compila tutti i campi"
                                }

                                !android.util.Patterns.EMAIL_ADDRESS
                                    .matcher(email)
                                    .matches() -> {

                                    errorMessage = "Inserisci una email valida"
                                }

                                password.length < 8 -> {
                                    errorMessage =
                                        "La password deve contenere almeno 8 caratteri"
                                }

                                password != confermaPassword -> {
                                    errorMessage =
                                        "Le password non coincidono"
                                }

                                else -> {
                                    // Registrazione corretta
                                    // TODO salvataggio account
                                    navController.navigate(Routes.Home) {

                                        popUpTo(Routes.Home) {
                                            inclusive = true
                                        }

                                        launchSingleTop = true
                                        restoreState = true
                                    }
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
                            navController.navigate(Routes.Login)

                        }
                    ) {
                        Text("Hai già un account? Accedi")
                    }
                }
            }
        }
    }
    if (isLandscape()) {
        RegisterLandscape(navController)
    } else {
        RegisterPortrait(navController)
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun RegisterScreenPreview() {
    MaterialTheme {
        RegisterScreen(
            navController = rememberNavController()
        )
    }
}