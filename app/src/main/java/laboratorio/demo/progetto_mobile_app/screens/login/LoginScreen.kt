package laboratorio.demo.progetto_mobile_app.screens.login

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import laboratorio.demo.progetto_mobile_app.R
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import laboratorio.demo.progetto_mobile_app.components.AppScaffold
import laboratorio.demo.progetto_mobile_app.components.AppTopBar
import laboratorio.demo.progetto_mobile_app.components.BackButton
import laboratorio.demo.progetto_mobile_app.components.isLandscape
import laboratorio.demo.progetto_mobile_app.navigation.Routes

@Composable
fun LoginScreen(navController: NavController) {
    var email by rememberSaveable  { mutableStateOf("") }
    var password by rememberSaveable  { mutableStateOf("") }
    var errorMessage by rememberSaveable  { mutableStateOf("") }

    if (isLandscape()) {
        LoginLandscape(
            navController = navController,

            email = email,
            password = password,
            errorMessage = errorMessage,

            onEmailChange = { email = it },
            onPasswordChange = { password = it },
            onErrorChange = { errorMessage = it }
        )
    } else {
        LoginPortrait(
            navController = navController,

            email = email,
            password = password,
            errorMessage = errorMessage,

            onEmailChange = { email = it },
            onPasswordChange = { password = it },
            onErrorChange = { errorMessage = it }
        )
    }
//    LoginForm(
//        navController = navController
//    )

//    Box(
//        modifier = Modifier.fillMaxSize()
//    ) {
//
//        BackButton(
//            // navController = navController,
//            modifier = Modifier
//                .align(Alignment.TopStart)
//                .padding(16.dp),
//
//            onClick = {
//                navController.popBackStack(
//                    Routes.Home,
//                    false
//                )
//
////                navController.navigate(Routes.Home) {
////                    popUpTo(Routes.Home) {
////                        inclusive = false
////                    }
////                    launchSingleTop = true
////                    restoreState = true
////                }
//            }
//        )
    /*
    AppScaffold(

        title = "Login",

        onBackClick = {
            navController.popBackStack()
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(20.dp),


            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Top
        ) {

//            Row(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                BackButton(navController)
//            }

            Image(
                painter = painterResource(R.drawable.google_maps_image),
                contentDescription = null,
                modifier = Modifier.size(90.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

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
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),

                    verticalArrangement = Arrangement.spacedBy(10.dp),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Login account",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                        //style = MaterialTheme.typography.bodyLarge
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            // TODO: Login
                            errorMessage = ""

                            when {
                                email.isBlank() -> {
                                    errorMessage = "Inserisci l'email"
                                }

                                password.isBlank() -> {
                                    errorMessage = "Inserisci la password"
                                }

                                password.length < 8 -> {
                                    errorMessage = "La password deve contenere almeno 8 caratteri"
                                }

                                else -> {
                                    // Login corretto
                                    // TODO collegamento database/server
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
                        Text("Accedi")
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
                            navController.navigate(Routes.Register)
                        }
                    ) {
                        Text("Non hai un account? Registrati")
                    }
                }
            }
        }
    }*/
}


@Preview(
    showBackground = true,
    showSystemUi = true
)

@Composable fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            navController = rememberNavController()
        )
    }
}

//@Preview(
//    showBackground = true,
//    showSystemUi = true
//)
//@Composable
//fun LoginScreenPreview() {
//    MaterialTheme {
//        LoginScreen(
//            navController = rememberNavController()
//        )
//    }
//}