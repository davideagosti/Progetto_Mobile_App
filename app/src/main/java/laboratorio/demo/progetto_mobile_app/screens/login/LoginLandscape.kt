package laboratorio.demo.progetto_mobile_app.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import laboratorio.demo.progetto_mobile_app.R

@Composable
fun LoginLandscape(
    navController: NavController,

    email: String,
    password: String,
    errorMessage: String,

    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onErrorChange: (String) -> Unit
) {

    Column(
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
            painter = painterResource(
                R.drawable.google_maps_image
            ),

            contentDescription = "Logo Smart Travel Planner",

            modifier = Modifier.size(80.dp)
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        // =========================
        // FORM
        // =========================

        LoginForm(
            navController = navController,

            email = email,
            password = password,
            errorMessage = errorMessage,

            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onErrorChange = onErrorChange
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    widthDp = 900,
    heightDp = 450
)
@Composable
fun LoginLandscapePreview() {

    MaterialTheme {

        LoginLandscape(
            navController = rememberNavController(),

            email = "",
            password = "",
            errorMessage = "",

            onEmailChange = {},
            onPasswordChange = {},
            onErrorChange = {}
        )
    }
}

