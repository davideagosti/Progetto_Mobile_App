package laboratorio.demo.progetto_mobile_app.screens.editaccount

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
fun EditAccountLandscape(

    navController: NavController,

    nome: String,
    cognome: String,

    vecchiaPassword: String,
    nuovaPassword: String,
    confermaNuovaPassword: String,

    accountErrorMessage: String,
    accountSuccessMessage: String,

    passwordErrorMessage : String,
    passwordSuccessMessage : String,

    isLoading: Boolean,

    onNomeChange: (String) -> Unit,
    onCognomeChange: (String) -> Unit,

    onVecchiaPasswordChange: (String) -> Unit,
    onNuovaPasswordChange: (String) -> Unit,
    onConfermaNuovaPasswordChange: (String) -> Unit,

    onSaveClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onDeleteAccountClick: () -> Unit

) {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 30.dp,
                vertical = 15.dp
//                start = 20.dp,
//                end = 20.dp,
//                top = 60.dp,
//                bottom = 30.dp
            ),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Top

    ) {

        // ==================================
        // LOGO
        // ==================================

        Image(

            painter = painterResource(
                R.drawable.google_maps_image
            ),

            contentDescription =
            "Logo Smart Travel Planner",

            modifier =
            Modifier.size(80.dp)
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )


        // ==================================
        // FORM
        // ==================================

        EditAccountForm(

            navController = navController,

            nome = nome,
            cognome = cognome,

            vecchiaPassword = vecchiaPassword,
            nuovaPassword = nuovaPassword,
            confermaNuovaPassword = confermaNuovaPassword,

            accountErrorMessage = accountErrorMessage,
            accountSuccessMessage = accountSuccessMessage,

            passwordErrorMessage = passwordErrorMessage,
            passwordSuccessMessage = passwordSuccessMessage,

            isLoading = isLoading,

            onNomeChange = onNomeChange,
            onCognomeChange = onCognomeChange,

            onVecchiaPasswordChange = onVecchiaPasswordChange,
            onNuovaPasswordChange = onNuovaPasswordChange,
            onConfermaNuovaPasswordChange = onConfermaNuovaPasswordChange,

            onSaveClick = onSaveClick,
            onChangePasswordClick = onChangePasswordClick,
            onDeleteAccountClick = onDeleteAccountClick
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
fun EditAccountLandscapePreview() {

    MaterialTheme {

        EditAccountLandscape(
            navController = rememberNavController(),

            nome = "",
            cognome = "",

            vecchiaPassword = "",
            nuovaPassword = "",
            confermaNuovaPassword = "",

            accountErrorMessage = "",
            accountSuccessMessage = "",

            passwordErrorMessage = "",
            passwordSuccessMessage = "",

            isLoading = false,

            onNomeChange = {},
            onCognomeChange = {},

            onVecchiaPasswordChange = {},
            onNuovaPasswordChange = {},
            onConfermaNuovaPasswordChange = {},

            onSaveClick = {},
            onChangePasswordClick = {},
            onDeleteAccountClick = {}
        )
    }
}