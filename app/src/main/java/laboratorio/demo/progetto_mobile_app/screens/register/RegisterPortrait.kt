package laboratorio.demo.progetto_mobile_app.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

import laboratorio.demo.progetto_mobile_app.R
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

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
    onErrorChange: (String) -> Unit,
    onRegister: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                start = 20.dp,
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
            onErrorChange = onErrorChange,
            onRegister = onRegister
        )
    }
}

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
            onErrorChange = {},
            onRegister = {}
        )
    }
}