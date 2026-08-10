package laboratorio.demo.progetto_mobile_app.screens.register

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.res.painterResource

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import laboratorio.demo.progetto_mobile_app.R
import laboratorio.demo.progetto_mobile_app.components.AppScaffold
import laboratorio.demo.progetto_mobile_app.components.AppTopBar
import laboratorio.demo.progetto_mobile_app.components.isLandscape
import laboratorio.demo.progetto_mobile_app.navigation.Routes

@Composable
fun RegisterLandscape(
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
    onErrorChange: (String) -> Unit
) {

//    AppScaffold(
//
//        topBarTitle  = "Registrazione",
//
//        onBackClick = {
//            navController.popBackStack()
//        }
//
//    ) { innerPadding ->

//        Row(
//            modifier = Modifier
//                .fillMaxSize()
//                //.padding(innerPadding)
//                .verticalScroll(rememberScrollState())
//                .padding(20.dp),
//
//            horizontalArrangement = Arrangement.Center
//        ) {

            Column(
                //modifier = Modifier.weight(1f)
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 60.dp,
                        bottom = 30.dp
                    ),

                horizontalAlignment = Alignment.CenterHorizontally,

                verticalArrangement = Arrangement.Top
            ) {

                // =========================
                // LOGO
                // =========================

                Image(
                    painter = painterResource(R.drawable.google_maps_image),
                    contentDescription = "Logo Smart Travel Planner",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                //}


//            Column(
//                modifier = Modifier.weight(2f)
//            ) {

                // =========================
                // FORM
                // =========================

                RegisterForm(
                    navController = navController,

                    email = email,
                    nome = nome,
                    cognome = cognome,
                    password = password,
                    confermaPassword = confermaPassword,
                    errorMessage = errorMessage,

                    onEmailChange = onEmailChange,
                    onNomeChange = onNomeChange,
                    onCognomeChange = onCognomeChange,
                    onPasswordChange = onPasswordChange,
                    onConfermaPasswordChange = onConfermaPasswordChange,
                    onErrorChange = onErrorChange
                )

                Spacer(modifier = Modifier.height(20.dp))
            }

//            // CARD FORM
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                shape = RoundedCornerShape(20.dp),
//                colors = CardDefaults.cardColors(
//                    containerColor = Color(0xFFF2F2F2)
//                ),
//                elevation = CardDefaults.cardElevation(
//                    defaultElevation = 6.dp
//                )
//            ) {
//
//                Column(
//                    modifier = Modifier
////                        .fillMaxSize()
//                        .fillMaxWidth()
//                        .wrapContentHeight()
//                        .padding(20.dp),
//                    verticalArrangement = Arrangement.spacedBy(16.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//
//                    Text(
//                        text = "Registra e crea il tuo account",
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.Bold
//                        //style = MaterialTheme.typography.headlineMedium
//                        //style = MaterialTheme.typography.bodyLarge
//                    )
//
//                    //        Text(
//                    //            text = "Registrazione",
//                    //            style = MaterialTheme.typography.headlineMedium
//                    //        )
//
//                    OutlinedTextField(
//                        value = email,
//                        onValueChange = onEmailChange,
////                        onValueChange = { email = it },
//                        label = { Text("Email") },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                    OutlinedTextField(
//                        value = nome,
//                        onValueChange = onNomeChange,
//                        //onValueChange = { nome = it },
//                        label = { Text("Nome") },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                    OutlinedTextField(
//                        value = cognome,
//                        onValueChange = onCognomeChange,
//                        //onValueChange = { cognome = it },
//                        label = { Text("Cognome") },
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                    OutlinedTextField(
//                        value = password,
//                        onValueChange = onPasswordChange,
//                        //onValueChange = { password = it },
//                        label = { Text("Password") },
//                        visualTransformation = PasswordVisualTransformation(),
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                    OutlinedTextField(
//                        value = confermaPassword,
//                        onValueChange = onConfermaPasswordChange,
////                        onValueChange = {
////                            confermaPassword = it
////                        },
//                        label = {
//                            Text("Conferma Password")
//                        },
//                        visualTransformation = PasswordVisualTransformation(),
//                        modifier = Modifier.fillMaxWidth()
//                    )
//
//                    Button(
//                        onClick = {
//                            // TODO: registrazione
//                            onErrorChange("")
//                            //errorMessage = ""
//
//                            when {
//                                email.isBlank() ||
//                                        nome.isBlank() ||
//                                        cognome.isBlank() ||
//                                        password.isBlank() ||
//                                        confermaPassword.isBlank() -> {
//
//                                    onErrorChange("Compila tutti i campi")
//                                    //errorMessage = "Compila tutti i campi"
//                                }
//
//                                !Patterns.EMAIL_ADDRESS
//                                    .matcher(email)
//                                    .matches() -> {
//
//                                    onErrorChange("Inserisci una email valida")
//                                    //errorMessage = "Inserisci una email valida"
//                                }
//
//                                password.length < 8 -> {
//                                    onErrorChange("La password deve contenere almeno 8 caratteri")
//                                    /*errorMessage =
//                                        "La password deve contenere almeno 8 caratteri"*/
//                                }
//
//                                password != confermaPassword -> {
//                                    onErrorChange("Le password non coincidono")
//                                    /*errorMessage =
//                                        "Le password non coincidono"*/
//                                }
//
//                                else -> {
//                                    // Registrazione corretta
//                                    // TODO salvataggio account
//                                    navController.navigate(Routes.Home) {
//
//                                        popUpTo(Routes.Home) {
//                                            inclusive = true
//                                        }
//
//                                        launchSingleTop = true
//                                        restoreState = true
//                                    }
//                                }
//                            }
//                        },
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text("Registrati")
//                    }
//
//                    if (errorMessage.isNotEmpty()) {
//
//                        Text(
//                            text = errorMessage,
//                            //color = Color.Red,
//                            color = MaterialTheme.colorScheme.error,
//                            fontSize = 14.sp
//                        )
//
//                    }
//
//                    TextButton(
//                        onClick = {
//                            // TODO Login
//                            navController.navigate(Routes.Login)
//
//                        }
//                    ) {
//                        Text("Hai già un account? Accedi")
//                    }
//                }
//            }

            //}

        //}

//    RegisterPortrait(navController)

//    }
}

@Preview(
    showBackground = true,
    //showSystemUi = true,
    widthDp = 1000,
    heightDp = 900
)
@Composable
fun RegisterLandscapePreview() {

    MaterialTheme {

        RegisterLandscape(
            navController = rememberNavController(),
            email = "",
            nome = "",
            cognome = "",
            password = "",
            confermaPassword = "",
            errorMessage = "",

            onEmailChange = {},
            onNomeChange = {},
            onCognomeChange = {},
            onPasswordChange = {},
            onConfermaPasswordChange = {},
            onErrorChange = {}
        )

    }

}