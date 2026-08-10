package laboratorio.demo.progetto_mobile_app.screens.register

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
import laboratorio.demo.progetto_mobile_app.components.AppScaffold
import laboratorio.demo.progetto_mobile_app.components.AppTopBar
import laboratorio.demo.progetto_mobile_app.components.BackButton
import laboratorio.demo.progetto_mobile_app.navigation.Routes
import laboratorio.demo.progetto_mobile_app.components.isLandscape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPortrait(
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

//    Box(
//        modifier = Modifier.fillMaxSize()
//    ) {
//
//        BackButton(
////            navController = navController,
//            modifier = Modifier
//                .align(Alignment.TopStart)
//                .padding(16.dp)
//                .zIndex(1f),
//            onClick = {
//                navController.popBackStack(
//                    Routes.Home,
//                    false
//                )
//            }

//            onClick = {
//                navController.navigate(Routes.Home) {
//                    popUpTo(Routes.Home) {
//                        inclusive = false
//                    }
//                    launchSingleTop = true
//                    restoreState = true
//                }
//            }
//        )
//    AppScaffold(
//
//        topBarTitle  = "Registrazione",
//
//        onBackClick = {
//            navController.popBackStack()
//        }
//
//    ) { innerPadding ->

    Column(
        modifier = Modifier
            .fillMaxSize()
            //.fillMaxWidth()
            //.wrapContentHeight()
            //.padding(innerPadding)
            .verticalScroll(rememberScrollState())
            .padding(start = 20.dp,
                end = 20.dp,
                top = 70.dp,
                bottom = 20.dp
            ),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Top
    ) {

        // Logo
        Image(
            painter = painterResource(R.drawable.google_maps_image),
            contentDescription = null,
            modifier = Modifier.size(90.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

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
    }
}
//}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun RegisterPortraitPreview() {
    MaterialTheme {
        RegisterPortrait(
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